package com.unitedvision.tvkabel.document.pdf;

import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.unitedvision.tvkabel.entity.Pelanggan;

public class ExceptionPdfView extends CustomAbstractPdfView {
	private String message;

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

	@Override
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		decorateDocument(doc, "Laporan Kesalahan");
		setMessage((String)model.get("message"));
		
		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		doc.add(paragraph);
		
		return doc;
	}

	@Override
	protected void createContent(Paragraph paragraph, List<Pelanggan> list) {
		// not used
	}
}
