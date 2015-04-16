package com.unitedvision.tvkabel.document.pdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class TunggakanPdfView extends AlamatPdfView {
	private int tunggakan;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setTunggakan((int)model.get("tunggakan"));
		super.buildPdfDocument(model, doc, writer, request, response);
	}

	public void setTunggakan(int tunggakan) {
		this.tunggakan = tunggakan;
	}
	
	@Override
	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.add(new Paragraph("Laporan Tunggakan Pelanggan", fontTitle));
		paragraph.add(new Paragraph(String.format("Kelurahan : %s / Lingkungan : %d / Tunggakan : %d bulan", kelurahan, lingkungan, tunggakan), fontSubTitle));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(paragraph, 1);
	}
}
