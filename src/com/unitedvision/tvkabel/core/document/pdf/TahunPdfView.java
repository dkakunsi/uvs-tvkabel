package com.unitedvision.tvkabel.core.document.pdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.domain.Pelanggan;
import com.unitedvision.tvkabel.domain.Pembayaran;

public class TahunPdfView extends CustomAbstractPdfView {
	private int tahun;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setTahun((int)model.get("tahun"));

		decorateDocument(doc, String.format("Laporan Pembayaran Tahun %d", tahun));

		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createTable(model, paragraph);
		doc.add(paragraph);
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
		float[] columnWidths = {10f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);
		
		insertCell(table, "Pelanggan", align, 1, fontHeader, Rectangle.BOX);
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
			insertCell(table, pelanggan.getNama(), align, 1, fontContent, Rectangle.BOX);

			int counter = 0;
			for (Pembayaran pembayaran : pelanggan.getListPembayaran()) {
				if (pembayaran.getJumlahBayar() == 0) {
					insertCell(table, "", align, 1, fontContent, Rectangle.BOX);
				} else {
					insertCell(table, "lunas", align, 1, fontContent, Rectangle.BOX);
				}
				counter++;
			}
			
			for (int i = 1; i <= (12 - counter); i++) {
				insertCell(table, "", align, 1, fontContent, Rectangle.BOX);
			}
		}

		paragraph.add(table);
	}

	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(), 0.1f, 0.1f, 1f, 0.1f);
	}
}
