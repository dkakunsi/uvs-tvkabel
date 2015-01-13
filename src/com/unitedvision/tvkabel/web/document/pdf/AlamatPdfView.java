package com.unitedvision.tvkabel.web.document.pdf;

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
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;

public class AlamatPdfView extends CustomAbstractPdfView {
	private String kelurahan;
	private int lingkungan;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setKelurahan((String)model.get("kelurahan"));
		setLingkungan((int)model.get("lingkungan"));
		
		decorateDocument(doc, "Laporan Pelanggan Berdasarkan Alamat");
		
		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createTable(model, paragraph);
		doc.add(paragraph);
	}
	
	public void setKelurahan(String kelurahan) {
		this.kelurahan = kelurahan;
	}
	
	public void setLingkungan(int lingkungan) {
		this.lingkungan = lingkungan;
	}

	@Override
	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.add(new Paragraph("Laporan Pelanggan", fontTitle));
		paragraph.add(new Paragraph(String.format("Kelurahan : %s / Lingkungan : %d", kelurahan, lingkungan), fontSubTitle));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(paragraph, 1);
	}
	
	private void createTable(Map<String, Object> model, Paragraph paragraph) throws DocumentException {
		float[] columnWidths = {4f, 9f, 3f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);
		
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Tunggakan", align, 1, fontHeader, Rectangle.BOX);
		table.setHeaderRows(1);

		@SuppressWarnings("unchecked")
		final List<PelangganEntity> list = (List<PelangganEntity>)model.get("listPelanggan");
		for (PelangganEntity p : list) {
			insertCell(table, p.getKode(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, p.getNama(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, String.valueOf(p.getTunggakan()), align, 1, fontContent, Rectangle.BOX);
		}

		insertCell(table, "Jumlah Pelanggan", Element.ALIGN_RIGHT, 2, fontContent, Rectangle.BOX);
		insertCell(table, Integer.toString(list.size()), align, 1, fontContent, Rectangle.BOX);
		
		paragraph.add(table);
	}
}
