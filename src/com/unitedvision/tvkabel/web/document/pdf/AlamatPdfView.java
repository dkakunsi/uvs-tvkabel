package com.unitedvision.tvkabel.web.document.pdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;

public class AlamatPdfView extends CustomAbstractPdfView {
	private String kelurahan = "";
	private int lingkungan = 0;
	private float[] columnWidths = {3f, 8f, 3f, 2f};
	private boolean isNew = true;

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
		if (!kelurahan.equals(pelanggan.getNamaKelurahan()) || lingkungan != pelanggan.getLingkungan())
			return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		/*
		setKelurahan((String)model.get("kelurahan"));
		setLingkungan((int)model.get("lingkungan"));
		
		decorateDocument(doc, "Laporan Pelanggan Berdasarkan Alamat");
		
		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createTable(model, paragraph);
		doc.add(paragraph);
		
		return doc;
		*/

		List<List<PelangganEntity>> container = new ArrayList<>();
		List<PelangganEntity> part = new ArrayList<>();
		for (PelangganEntity pelanggan : (List<PelangganEntity>) model.get("listPelanggan")) {
			if (changeAlamat(pelanggan) && isNew == false) {
				container.add(part);
				part = new ArrayList<>();
			}
			
			part.add(pelanggan);
			isNew = false;
		}

		container.add(part);
		createDoc(container, doc);
		
		return doc;
	}
	
	private void createDoc(List<List<PelangganEntity>> list, Document doc) throws DocumentException {
		for (List<PelangganEntity> listPelanggan : list) {
			create(listPelanggan, doc);
		}
	}
	
	private void create(List<PelangganEntity> list, Document doc) throws DocumentException {
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
	
	private void createContent(Paragraph paragraph, List<PelangganEntity> list) {
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100f);
		
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Kontak", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Tunggakan", align, 1, fontHeader, Rectangle.BOX);
		table.setHeaderRows(1);

		for (PelangganEntity pelanggan : list) {
			insertCell(table, pelanggan.getKode(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, pelanggan.getNama(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, createKontak(pelanggan), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, String.format("%d bulan", pelanggan.getTunggakan()), align, 1, fontContent, Rectangle.BOX);
		}

		insertCell(table, "Jumlah Pelanggan", Element.ALIGN_RIGHT, 2, fontContent, Rectangle.BOX);
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
