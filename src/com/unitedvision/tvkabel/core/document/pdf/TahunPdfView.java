package com.unitedvision.tvkabel.core.document.pdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.core.document.pdf.CustomAbstractPdfView;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;

public class TahunPdfView extends CustomAbstractPdfView {
	private int tahun;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		create(model, doc);
	}
	
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		setTahun((int)model.get("tahun"));

		decorateDocument(doc, String.format("Laporan Pembayaran Tahun %d", tahun));

		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createTable(model, paragraph);
		doc.add(paragraph);
		
		return doc;
	}
	
	private void setTahun(int tahun) {
		this.tahun = tahun;
	}

	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.add(new Paragraph("Laporan Pembayaran", fontTitle));
		paragraph.add(new Paragraph(String.format("Tahun : %d", tahun), fontSubTitle));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(paragraph, 1);
	}
	
	private void createTable(Map<String, Object> model, Paragraph paragraph) throws DocumentException {
		float[] columnWidths = {2f, 9f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Nama", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "JAN", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "FEB", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "MAR", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "APR", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "MEI", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "JUN", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "JUL", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "AGU", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "SEP", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "OKT", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "NOV", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "DES", align, 1, fontHeader, Rectangle.BOX);
		table.setHeaderRows(1);

		@SuppressWarnings("unchecked")
		final List<Pelanggan> list = (List<Pelanggan>)model.get("rekap");
		for (Pelanggan pelanggan : list) {
			Font customFont = getCustomFont(pelanggan.getTunggakan());
			
			insertCell(table, pelanggan.getKode(), align, 1, customFont, Rectangle.BOX);
			insertCell(table, pelanggan.getNama(), align, 1, customFont, Rectangle.BOX);
			insertListPembayaran(pelanggan, table, customFont);
		}

		paragraph.add(table);
	}
	
	private void insertListPembayaran(Pelanggan pelanggan, PdfPTable table, Font customFont) {
		int counter = 0;
		for (Pembayaran pembayaran : pelanggan.getListPembayaran()) {
			insertCell(table, getKeteranganPembayaran(pembayaran), align, 1, customFont, Rectangle.BOX);
			counter++;
		}
		
		for (int i = 1; i <= (12 - counter); i++) {
			insertCell(table, "", align, 1, customFont, Rectangle.BOX);
		}
	}
	
	private String getKeteranganPembayaran(Pembayaran pembayaran) {
		if (pembayaran.getJumlahBayar() == 0)
			return "";
		return "lunas";
	}

	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(), 0.1f, 0.1f, 1f, 0.1f);
	}
}
