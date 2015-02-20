package com.unitedvision.tvkabel.core.document.pdf;

import java.awt.Color;

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
	public static final int fontTitleSize = 14;
	public static final int fontTitleStyle = Font.BOLD;
	public static final int fontTitleType = Font.TIMES_ROMAN;
	protected Font fontTitle = new Font(fontTitleType, fontTitleSize, fontTitleStyle);

	public static final int fontSubtitleSize = 14;
	public static final int fontSubtitleType = Font.TIMES_ROMAN;
	protected Font fontSubTitle = new Font(fontSubtitleType, fontSubtitleSize);

	public static final int fontHeaderSize = 11;
	public static final int fontHeaderStyle = Font.BOLD;
	public static final int fontHeaderType = Font.TIMES_ROMAN;
	protected Font fontHeader = new Font(fontHeaderType, fontHeaderSize, fontHeaderStyle);

	public static final int fontContentSize = 10;
	public static final int fontContentType = Font.TIMES_ROMAN;
	protected Font fontContent = new Font(fontContentType, fontContentSize);

	protected int align = Element.ALIGN_LEFT;
	protected float tablePercentage = 98f;
	
	protected float minimumCellHeight = 10f;

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
			cell.setMinimumHeight(minimumCellHeight);
		}

		table.addCell(cell);
	}

	protected void insertCell(PdfPTable table, String text, int align, int colspan, Font font, int border, float minimumCellHeight){
		this.minimumCellHeight = minimumCellHeight;
		insertCell(table, text, align, colspan, font, border);
	}

	protected static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++)
	      paragraph.add(new Paragraph(" "));
	}
	
	protected Font getCustomFont(int tunggakan) {
		Font takWajar = new Font(fontContentType, fontContentSize, Font.NORMAL, Color.BLUE);
		Font lunas = new Font(fontContentType, fontContentSize, Font.NORMAL, Color.GREEN);
		Font rekomPutus = new Font(fontContentType, fontContentSize, Font.NORMAL, Color.RED);
		
		if (tunggakan > 3)
			return rekomPutus;
		if (tunggakan > 1)
			return takWajar;
		if (tunggakan < 1)
			return lunas;
		return fontContent;
	}
}
