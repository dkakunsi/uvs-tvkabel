package com.unitedvision.tvkabel.document.pdf;

import java.time.Month;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Pembayaran;

public class KartuPelangganPdfView extends DefaultKartuPelangganPdfView {
	private boolean contained;
	private int tahun;
	
	@SuppressWarnings("unchecked")
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		Object modelPelanggan = model.get("pelanggan");
		contained = (boolean)model.get("pembayaran");
		tahun = (int)model.get("tahun");

		if (modelPelanggan instanceof Pelanggan) {
			createCard(doc, (Pelanggan)modelPelanggan);
		} else if (modelPelanggan instanceof List) {
			createCard(doc, (List<Pelanggan>) modelPelanggan);
		}
		
		return doc;
	}

	//Multiple card
	protected void createCard(Document doc, List<Pelanggan> listPelanggan) throws DocumentException {
		for (Pelanggan pelanggan : listPelanggan)
			createCard(doc, pelanggan);
	}
	
	//Single card
	protected void createCard(Document doc, Pelanggan pelanggan) throws DocumentException {
		if (perusahaan == null)
			perusahaan = pelanggan.getPerusahaan();
		super.createPage(doc, pelanggan);
	}
	
	protected void createHeadTable(Paragraph paragraph, Pelanggan pelanggan) {
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, createKodeDanNomorBuku(pelanggan), align, 3, fontHeader, Rectangle.NO_BORDER);

		insertCell(table, "Nama", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getNama(), align, 3, fontHeader, Rectangle.NO_BORDER);
		
		insertCell(table, "Kontak", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, createKontak(pelanggan), align, 3, fontContent, Rectangle.NO_BORDER);

		insertCell(table, "Jmlh TV", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, Integer.toString(pelanggan.getJumlahTv()), align, 1, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Iuran", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, Long.toString(pelanggan.getIuran()), align, 1, fontHeader, Rectangle.NO_BORDER);
		
		insertCell(table, "Kelurahan", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getNamaKelurahan(), align, 1, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Lingkungan", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, Integer.toString(pelanggan.getLingkungan()), align, 1, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Detail", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getDetailAlamat(), align, 3, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Tgl Mulai", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, createTanggalMulai(pelanggan), align, 1, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Tahun Tagih", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, Integer.toString(tahun), align, 1, fontContent, Rectangle.NO_BORDER);

		paragraph.add(table);
	}

	protected void createPembayaranTable(Paragraph paragraph, Pelanggan pelanggan, PdfPTable table) {
		createPembayaranTableHeader(table);

		if (contained) {
			setContainedTable(table, pelanggan);
		} else {
			insertEmptyCells(table, 1);
		}

		paragraph.add(table);
	}
	
	private void setContainedTable(PdfPTable table, Pelanggan pelanggan) {
		List<Pembayaran> list = ((Pelanggan)pelanggan).getListPembayaran();
		int i = 1;
		
		if (list != null) {
			for (Pembayaran pembayaran : list) {
				String month = Month.of(i).name();

				if (pembayaran.getKode().equals("DEFAULT")) {
					insertEmptyCell(table, month);
				} else {
					insertCell(table, month.substring(0, 3), Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
					insertCell(table, createTanggalBayar(pembayaran), Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
					insertCell(table, "OK", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
					insertCell(table, "OK", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
				}
				
				i++;
			}
		}
		
		insertEmptyCells(table, i);
	}
	
	private String createTanggalMulai(Pelanggan pelanggan) {
		return createTanggal(pelanggan.getTanggalMulai());
	}
	
	private String createTanggalBayar(Pembayaran pembayaran) {
		return createTanggal(pembayaran.getTanggalBayar());
	}
	
	private String createKodeDanNomorBuku(Pelanggan pelanggan) {
		String kodeDanNomor = String.format("%s", pelanggan.getKode());

		if (pelanggan.getNomorBuku() != null && !(pelanggan.getNomorBuku().equals("")))
			kodeDanNomor = String.format("%s / %s", kodeDanNomor, pelanggan.getNomorBuku());
		return kodeDanNomor;
	}
	
	private String createKontak(Pelanggan pelanggan) {
		String kontak = pelanggan.getHp();

		if (pelanggan.getTelepon() != null && !(pelanggan.getTelepon().equals("")))
			kontak = String.format("%s, %s", kontak, pelanggan.getHp());
		return kontak;
	}
	
	@Override
	protected Document newDocument() {
		return new Document(new Rectangle(297, 466), 0.1f, 0.1f, 0.1f, 0.1f);
	}
}
