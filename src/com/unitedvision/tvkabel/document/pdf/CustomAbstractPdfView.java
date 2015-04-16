package com.unitedvision.tvkabel.document.pdf;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.util.DateUtil;

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

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		create(model, doc);
	}
	
	public abstract Document create(Map<String, Object> model, Document doc) throws DocumentException;
	protected abstract void createTitle(Paragraph paragraph) throws DocumentException;
	protected abstract void createContent(Paragraph paragraph, List<Pelanggan> list);

	protected void decorateDocument(Document doc, String title) {
		doc.addAuthor("United Vision");
		doc.addCreationDate();
		doc.addTitle(title);
	}

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
		Font wajar = new Font(fontContentType, fontContentSize, Font.NORMAL, Color.BLUE);
		Font takWajar = new Font(fontContentType, fontContentSize, Font.NORMAL, Color.BLACK);
		Font lunas = new Font(fontContentType, fontContentSize, Font.NORMAL, Color.GREEN);
		Font rekomPutus = new Font(fontContentType, fontContentSize, Font.NORMAL, Color.RED);
		
		if (tunggakan > 3)
			return rekomPutus;
		if (tunggakan > 1)
			return takWajar;
		if (tunggakan < 1)
			return lunas;
		return wajar;
	}
	
	protected String createTanggal(Date tanggal) {
		return DateUtil.toUserString(tanggal, "-");
	}	
}
