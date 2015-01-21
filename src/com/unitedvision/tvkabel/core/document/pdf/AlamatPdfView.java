package com.unitedvision.tvkabel.core.document.pdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.core.document.pdf.CustomAbstractPdfView;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;

public class AlamatPdfView extends CustomAbstractPdfView {
	private String kelurahan = "";
	private int lingkungan = 0;
	private float[] columnWidths = {3f, 8f, 3f, 2f};
	private boolean first;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		create(model, doc);
	}
	
	public void setKelurahan(String kelurahan) {
		this.kelurahan = kelurahan;
	}
	
	public void setLingkungan(int lingkungan) {
		this.lingkungan = lingkungan;
	}

	private boolean changeAlamat(Pelanggan pelanggan) {
		if (!kelurahan.equals(pelanggan.getNamaKelurahan()) || lingkungan != pelanggan.getLingkungan()) {
			setKelurahan(pelanggan.getNamaKelurahan());
			setLingkungan(pelanggan.getLingkungan());

			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		first = true;
		
		List<List<Pelanggan>> container = new ArrayList<>();
		List<Pelanggan> part = new ArrayList<>();
		/**
		 * Divide {@code listPelanggan} into some list as much as {@link Kelurahan} and {@code lingkungan}.
		 * So, every {@code listPelanggan} will contain {@link Pelanggan} with the same {@link Kelurahan} and {@code lingkungan}.
		 */
		for (Pelanggan pelanggan : (List<Pelanggan>) model.get("listPelanggan")) {
			/**
			 * Create a new list if {@code alamat} is changes and it's not the first loop.
			 */
			if (changeAlamat(pelanggan) && first == false) {
				container.add(part);
				part = new ArrayList<>();
			}
			
			part.add(pelanggan);
			first = false;
		}

		container.add(part);
		createDoc(container, doc);
		
		return doc;
	}

	/**
	 * Create document for each {@link Alamat} and {@code lingkungan}.
	 * @param list
	 * @param doc
	 * @throws DocumentException
	 */
	private void createDoc(List<List<Pelanggan>> list, Document doc) throws DocumentException {
		for (List<Pelanggan> listPelanggan : list) {
			create(listPelanggan, doc);
		}
	}
	
	private void create(List<Pelanggan> list, Document doc) throws DocumentException {
		doc.newPage();
		
		Pelanggan pelanggan = list.get(0);
		setKelurahan(pelanggan.getNamaKelurahan());
		setLingkungan(pelanggan.getLingkungan());
		
		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createContent(paragraph, list);
		
		doc.add(paragraph);
	}

	@Override
	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.add(new Paragraph("Laporan Pelanggan", fontTitle));
		paragraph.add(new Paragraph(String.format("Kelurahan : %s / Lingkungan : %d", kelurahan, lingkungan), fontSubTitle));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(paragraph, 1);
	}
	
	private void createContent(Paragraph paragraph, List<Pelanggan> list) {
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Kontak", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Tunggakan", align, 1, fontHeader, Rectangle.BOX);
		table.setHeaderRows(1);

		for (Pelanggan pelanggan : list) {
			Font customFont = getCustomFont(pelanggan.getTunggakan());
			
			insertCell(table, pelanggan.getKode(), align, 1, customFont, Rectangle.BOX);
			insertCell(table, pelanggan.getNama(), align, 1, customFont, Rectangle.BOX);
			insertCell(table, createKontak(pelanggan), align, 1, customFont, Rectangle.BOX);
			insertCell(table, String.format("%d bulan", pelanggan.getTunggakan()), align, 1, customFont, Rectangle.BOX);
		}

		insertCell(table, "Jumlah Pelanggan", Element.ALIGN_RIGHT, 3, fontContent, Rectangle.BOX);
		insertCell(table, Integer.toString(list.size()), align, 1, fontContent, Rectangle.BOX);
		
		paragraph.add(table);
	}
	
	private String createKontak(Pelanggan pelanggan) {
		if (!pelanggan.getHp().equals(""))
			return pelanggan.getHp();
		if (!pelanggan.getTelepon().equals(""))
			return pelanggan.getTelepon();
		
		return "";
	}
}
