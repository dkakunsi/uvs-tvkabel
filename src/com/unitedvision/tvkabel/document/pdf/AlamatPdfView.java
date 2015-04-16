package com.unitedvision.tvkabel.document.pdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.unitedvision.tvkabel.document.pdf.CustomAbstractPdfView;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;

public class AlamatPdfView extends CustomAbstractPdfView {
	/**
	 * Nama Kelurahan.
	 */
	protected String kelurahan = "";
	protected int lingkungan = 0;
	private float[] columnWidths = {1f, 3f, 6f, 3f, 3f, 4f};
	/**
	 * Untuk menentukan apakah pelanggan merupakan yang pertama untuk alamat tertentu.
	 */
	private boolean first;
	
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
		/*
		 * Memisahkan {@code listPelanggan} menjadi beberapa list - sebanyak jumlah {@code lingkungan} untuk setiap {@link Kelurahan}.
		 * Sehingga, setiap {@code listPelanggan} akan memuat data {@link Pelanggan} dengan {@link Kelurahan} dan {@code lingkungan} yang sama.
		 */
		for (Pelanggan pelanggan : (List<Pelanggan>) model.get("listPelanggan")) {
			/*
			 * Buat list baru jika {@code alamat} berubah dan bukan merupakan yang pertama.
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
	 * Buat dokumen untuk masing-masing {@link Alamat} dan {@code lingkungan}.
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
	
	protected void createContent(Paragraph paragraph, List<Pelanggan> list) {
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "No", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Kontak", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Tunggakan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Terakhir", align, 1, fontHeader, Rectangle.BOX);
		table.setHeaderRows(1);

		for (Pelanggan pelanggan : list) {
			Font customFont = getCustomFont(pelanggan.getTunggakan());
			
			insertCell(table, createNomorBuku(pelanggan), align, 1, customFont, Rectangle.BOX);
			insertCell(table, pelanggan.getKode(), align, 1, customFont, Rectangle.BOX);
			insertCell(table, pelanggan.getNama(), align, 1, customFont, Rectangle.BOX);
			insertCell(table, createKontak(pelanggan), align, 1, customFont, Rectangle.BOX);
			insertCell(table, createTunggakan(pelanggan), align, 1, customFont, Rectangle.BOX);
			insertCell(table, createPembayaranTerakhir(pelanggan), align, 1, customFont, Rectangle.BOX);
			
		}

		insertCell(table, "Jumlah Pelanggan", Element.ALIGN_RIGHT, 5, fontContent, Rectangle.BOX);
		insertCell(table, Integer.toString(list.size()), align, 1, fontContent, Rectangle.BOX);
		
		paragraph.add(table);
	}
	
	private String createNomorBuku(Pelanggan pelanggan) {
		if (pelanggan.getNomorBuku() != null && !(pelanggan.getNomorBuku().equals("")))
			return pelanggan.getNomorBuku();
		return "";
	}
	
	private String createTunggakan(Pelanggan pelanggan) {
		String tunggakan = Integer.toString(pelanggan.getTunggakan()).replace("-", "+");
		return String.format("%s bulan", tunggakan);
	}
	
	private String createPembayaranTerakhir(Pelanggan pelanggan) {
		Pembayaran terakhir = pelanggan.getPembayaranTerakhir();
		if (terakhir != null)
			return terakhir.getTagihanStr();
		return "";
	}
	
	private String createKontak(Pelanggan pelanggan) {
		if (!pelanggan.getHp().equals(""))
			return pelanggan.getHp();
		if (!pelanggan.getTelepon().equals(""))
			return pelanggan.getTelepon();
		
		return "";
	}
}
