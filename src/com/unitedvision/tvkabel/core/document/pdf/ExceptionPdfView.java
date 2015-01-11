package com.unitedvision.tvkabel.core.document.pdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class ExceptionPdfView extends CustomAbstractPdfView {
	private String message;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		decorateDocument(doc, "Laporan Kesalahan");
		setMessage((String)model.get("message"));
		
		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		doc.add(paragraph);
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.add(new Paragraph("Tidak Bisa Menampilkan File", fontTitle));
		paragraph.add(new Paragraph(message, fontContent));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(paragraph, 1);
	}
}
