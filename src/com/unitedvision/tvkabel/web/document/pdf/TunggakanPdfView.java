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

public class TunggakanPdfView extends CustomAbstractPdfView {
	private int tunggakan;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		decorateDocument(doc, "Laporan Tunggakan Pelanggan");
		setTunggakan((int)model.get("tunggakan"));
		
		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createTable(model, paragraph);
		doc.add(paragraph);
	}

	public void setTunggakan(int tunggakan) {
		this.tunggakan = tunggakan;
	}
	
	@Override
	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.add(new Paragraph("Laporan Pelanggan", fontTitle));
		paragraph.add(new Paragraph(String.format("Tunggakan = %d bulan", tunggakan), fontSubTitle));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(paragraph, 1);
	}
	
	private void createTable(Map<String, Object> model, Paragraph paragraph) throws DocumentException {
		float[] columnWidths = {3f, 6f, 3f, 2f, 7f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Kelurahan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Lingk.", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Detail Alamat", align, 1, fontHeader, Rectangle.BOX);
		table.setHeaderRows(1);

		@SuppressWarnings("unchecked")
		final List<PelangganEntity> list = (List<PelangganEntity>)model.get("listPelanggan");
		for (PelangganEntity p : list) {
			insertCell(table, p.getKode(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, p.getNama(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, p.getNamaKelurahan(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, Integer.toString(p.getLingkungan()), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, p.getDetailAlamat(), align, 1, fontContent, Rectangle.BOX);
		}

		insertCell(table, "Jumlah Pelanggan", Element.ALIGN_RIGHT, 4, fontContent, Rectangle.BOX);
		insertCell(table, Integer.toString(list.size()), align, 1, fontContent, Rectangle.BOX);
		
		paragraph.add(table);
	}
}
