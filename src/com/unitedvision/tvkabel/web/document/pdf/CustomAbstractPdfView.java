package com.unitedvision.tvkabel.web.document.pdf;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public abstract class CustomAbstractPdfView extends AbstractPdfView {
	protected Font fontTitle = new Font(Font.TIMES_ROMAN, 14, Font.BOLD);
	protected Font fontSubTitle = new Font(Font.TIMES_ROMAN, 14);
	protected Font fontHeader = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
	protected Font fontContent = new Font(Font.TIMES_ROMAN, 11);
	protected int align = Element.ALIGN_LEFT;

	protected void decorateDocument(Document doc, String title) {
		doc.addAuthor("United Vision");
		doc.addCreationDate();
		doc.addTitle(title);
	}

	protected abstract void createTitle(Paragraph paragraph) throws DocumentException;

	protected void insertCell(PdfPTable table, String text, int align, int colspan, Font font, int border){
		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setBorder(border);

		if(text.trim().equalsIgnoreCase("")){
			cell.setMinimumHeight(10f);
		}

		table.addCell(cell);
	}

	protected static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	}
	
}
