package com.unitedvision.tvkabel.document.pdf;

import java.awt.Color;
import java.time.Month;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.unitedvision.tvkabel.document.pdf.CustomAbstractPdfView;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.entity.Perusahaan;

public class DefaultKartuPelangganPdfView extends CustomAbstractPdfView {
	protected Perusahaan perusahaan;
	protected float[] columnWidths = {3.5f, 5f, 4f, 3.5f};
	protected Font fontTableHeader = new Font(Font.TIMES_ROMAN, 11);
	public static Font fontTableContent = new Font(Font.TIMES_ROMAN, 11);
	
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		perusahaan = (Perusahaan)model.get("perusahaan");
		decorateDocument(doc, String.format("Kartu Pelanggan %s", perusahaan.getNama()));
		createPage(doc, null);
		
		return doc;
	}
	
	protected void createPage(Document doc, Pelanggan pelanggan) throws DocumentException {
		this.minimumCellHeight = 22f;
		doc.newPage();

		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createHeadTable(paragraph, pelanggan);
		createPembayaranTable(paragraph, pelanggan, new PdfPTable(columnWidths));
		doc.add(paragraph);
	}
	
	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.add(new Paragraph(String.format("%s", perusahaan.getNama()), new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
		String namaPT = perusahaan.getNamaPT();
		if (namaPT != null && !namaPT.equals("")) {
			paragraph.add(new Paragraph(namaPT, new Font(Font.TIMES_ROMAN, 8)));
		}
	}

	//Pelanggan is not used
	protected void createHeadTable(Paragraph paragraph, Pelanggan pelanggan) {
		final Font tmpFont = new Font(Font.TIMES_ROMAN, 11, Font.NORMAL, Color.WHITE);
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(tablePercentage);
		
		String defString = "xxx"; // hanya untuk mengatur ukuran cell
		
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, defString, align, 3, tmpFont, Rectangle.NO_BORDER);

		insertCell(table, "Nama", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, defString, align, 3, tmpFont, Rectangle.NO_BORDER);
		
		insertCell(table, "Kontak", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, defString, align, 3, tmpFont, Rectangle.NO_BORDER);

		insertCell(table, "Jmlh TV", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, defString, align, 1, tmpFont, Rectangle.NO_BORDER);
		
		insertCell(table, "Iuran", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, defString, align, 1, tmpFont, Rectangle.NO_BORDER);
		
		insertCell(table, "Kelurahan", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, defString, align, 1, tmpFont, Rectangle.NO_BORDER);
		
		insertCell(table, "Lingkungan", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, defString, align, 1, tmpFont, Rectangle.NO_BORDER);
		
		insertCell(table, "Detail", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, defString, align, 3, tmpFont, Rectangle.NO_BORDER);
		
		insertCell(table, "Tgl Mulai", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, defString, align, 1, tmpFont, Rectangle.NO_BORDER);
		
		insertCell(table, "Tahun Tagih", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, defString, align, 1, tmpFont, Rectangle.NO_BORDER);

		paragraph.add(table);
	}
	
	protected void createPembayaranTableHeader(PdfPTable table) {
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "Bulan", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);
		insertCell(table, "Tgl Bayar", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);
		insertCell(table, "Pelanggan", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);
		insertCell(table, "Penagih", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);
	}
	
	protected void createPembayaranTable(Paragraph paragraph, Pelanggan pelanggan, PdfPTable table) {
		createPembayaranTableHeader(table);
		insertEmptyCells(table, 1);
		paragraph.add(table);
	}
	
	protected void insertEmptyCells(PdfPTable table, int i) {
		for (int x = i; x <= 12; x++) {
			String month = Month.of(x).name();
			insertEmptyCell(table, month);
		}
	}
	
	protected void insertEmptyCell(PdfPTable table, String month) {
		insertCell(table, month.substring(0, 3), Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
		insertCell(table, "", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
		insertCell(table, "", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
		insertCell(table, "", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
	}
	
	@Override
	protected Document newDocument() {
		return new Document(new Rectangle(297, 466), 0.1f, 0.1f, 0.1f, 0.1f);
	}

	@Override
	protected void createContent(Paragraph paragraph, List<Pelanggan> list) {
		//not used
	}
}
