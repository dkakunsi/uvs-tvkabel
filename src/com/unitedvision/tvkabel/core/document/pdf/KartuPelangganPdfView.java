package com.unitedvision.tvkabel.core.document.pdf;

import java.time.Month;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.util.DateUtil;

public class KartuPelangganPdfView extends DefaultKartuPelangganPdfView {
	private boolean contained;
	
	public void setContained(boolean bool) {
		this.contained = bool;
	}
	
	@SuppressWarnings("unchecked")
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		Object modelPelanggan = model.get("pelanggan");
		setContained((boolean)model.get("pembayaran"));

		if (modelPelanggan instanceof Pelanggan) {
			createCard(doc, (Pelanggan)modelPelanggan);
		} else if (modelPelanggan instanceof List) {
			createCard(doc, (List<Pelanggan>) modelPelanggan);
		}
		
		return doc;
	}
	
	private void createCard(Document doc, List<Pelanggan> listPelanggan) throws DocumentException {
		for (Pelanggan pelanggan : listPelanggan) {
			createCard(doc, pelanggan);
		}
	}
	
	protected void createCard(Document doc, Pelanggan pelanggan) throws DocumentException {
		if (perusahaan == null)
			setPerusahaan(pelanggan.getPerusahaan());
		super.createCard(doc, pelanggan);
	}
	
	protected void createHeadTable(Paragraph paragraph, Pelanggan pelanggan) throws DocumentException {
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getKode(), align, 3, fontHeader, Rectangle.NO_BORDER);

		insertCell(table, "Nama", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getNama(), align, 3, fontHeader, Rectangle.NO_BORDER);
		
		insertCell(table, "Kontak", align, 1, fontHeader, Rectangle.NO_BORDER);
		String kontak = pelanggan.getTelepon();
		if (!kontak.equals("")) {
			kontak = String.format("%s, %s", kontak, pelanggan.getHp());
		} else {
			kontak = pelanggan.getHp();
		}
		insertCell(table, kontak, align, 3, fontContent, Rectangle.NO_BORDER);

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
		insertCell(table, 
				DateUtil.toUserString(pelanggan.getTanggalMulai(), "-"), 
				align, 1, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Tahun Tagih", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, 
				Integer.toString(DateUtil.getYear(DateUtil.getNow())), 
				align, 1, fontContent, Rectangle.NO_BORDER);

		paragraph.add(table);
	}

	protected void createPembayaranTable(Paragraph paragraph, Pelanggan pelanggan) {
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "Bulan", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);
		insertCell(table, "Tgl Bayar", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);
		insertCell(table, "Pelanggan", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);
		insertCell(table, "Penagih", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);

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
					String tanggalBayar = DateUtil.toUserString(pembayaran.getTanggalBayar(), "-");
					
					insertCell(table, month.substring(0, 3), Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
					insertCell(table, tanggalBayar, Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
					insertCell(table, "OK", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
					insertCell(table, "OK", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
				}
				
				i++;
			}
		}
		
		insertEmptyCells(table, i);
	}

	@Override
	protected void insertCell(PdfPTable table, String text, int align, int colspan, Font font, int border) {
		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setBorder(border);

		if(text.trim().equalsIgnoreCase("")){
			cell.setMinimumHeight(22f);
		}

		table.addCell(cell);
	};
	
	@Override
	protected Document newDocument() {
		return new Document(new Rectangle(297, 466), 0.1f, 0.1f, 0.1f, 0.1f);
	}
}
