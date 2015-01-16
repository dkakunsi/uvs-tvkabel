package com.unitedvision.tvkabel.web.document.pdf;

import java.time.Month;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.core.domain.Pelanggan;
import com.unitedvision.tvkabel.core.domain.Perusahaan;
import com.unitedvision.tvkabel.persistence.entity.PelangganEntity;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.util.DateUtil;

public class KartuPelangganPdfView extends CustomAbstractPdfView {
	private Perusahaan perusahaan;
	private boolean contained;
	private float[] columnWidths = {3.5f, 5f, 4.5f, 3f};
	protected Font fontTableHeader = new Font(Font.TIMES_ROMAN, 11);
	public static Font fontTableContent = new Font(Font.TIMES_ROMAN, 11);
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		create(model, doc);
	}
	
	@SuppressWarnings("unchecked")
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		Object modelPelanggan = model.get("pelanggan");
		setContained((int)model.get("pembayaran"));

		if (modelPelanggan instanceof PelangganEntity) {
			createCard(doc, (Pelanggan)modelPelanggan);
		} else if (modelPelanggan instanceof List) {
			createCard(doc, (List<Pelanggan>) modelPelanggan);
		}
		
		return doc;
	}
	
	private void createCard(Document doc, List<Pelanggan> listPelanggan) throws DocumentException {
		for (Pelanggan pelanggan : listPelanggan) {
			createCard(doc, pelanggan);
		}
	}
	
	private void createCard(Document doc, Pelanggan pelanggan) throws DocumentException {
		if (perusahaan == null)
			setPerusahaan(pelanggan.getPerusahaan());

		decorateDocument(doc, String.format("Kartu Pelanggan %s", perusahaan.getNama()));
		createPage(doc, pelanggan);
	}
	
	public void setPerusahaan(Perusahaan perusahaan) {
		this.perusahaan = perusahaan;
	}
	
	public void setContained(boolean bool) {
		this.contained = bool;
	}
	
	public void setContained(int i) {
		if (i == 1) {
			setContained(true);
		} else {
			setContained(false);
		}
	}
	
	protected void createPage(Document doc, Pelanggan pelanggan) throws DocumentException {
		doc.newPage();

		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createHeadTable(paragraph, pelanggan);
		createPembayaranTable(paragraph, pelanggan);
		doc.add(paragraph);
	}
	
	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.add(new Paragraph(String.format("%s", perusahaan.getNama()), new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
		paragraph.add(new Paragraph("PT. Aspetika Manasa Sulut - Manado", new Font(Font.TIMES_ROMAN, 8)));
		paragraph.add(new Paragraph("Kartu Pembayaran Pelanggan", new Font(Font.TIMES_ROMAN, 14)));
	}
	
	private void createHeadTable(Paragraph paragraph, Pelanggan pelanggan) throws DocumentException {
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);
		
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getKode(), align, 3, fontContent, Rectangle.NO_BORDER);

		insertCell(table, "Nama", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getNama(), align, 3, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Kontak", align, 1, fontHeader, Rectangle.NO_BORDER);
		String kontak = pelanggan.getTelepon();
		if (!kontak.equals("")) {
			kontak = String.format("%s, %s", kontak, pelanggan.getHp());
		} else {
			kontak = pelanggan.getHp();
		}
		insertCell(table, kontak, align, 3, fontContent, Rectangle.NO_BORDER);

		insertCell(table, "Jmlh TV", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, Integer.toString(pelanggan.getJumlahTv()), align, 1, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Iuran", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, Long.toString(pelanggan.getIuran()), align, 1, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Kel.", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getNamaKelurahan(), align, 1, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Lingk.", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, Integer.toString(pelanggan.getLingkungan()), align, 1, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Detail", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getDetailAlamat(), align, 3, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Tgl Mulai", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, 
				DateUtil.toUserString(pelanggan.getTanggalMulai(), "-"), 
				align, 1, fontContent, Rectangle.NO_BORDER);
		
		insertCell(table, "Tahun Tagih", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, 
				Integer.toString(DateUtil.getYear(DateUtil.getNow())), 
				align, 1, fontContent, Rectangle.NO_BORDER);

		paragraph.add(table);
	}

	private void createPembayaranTable(Paragraph paragraph, Pelanggan pelanggan) {
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);
		
		insertCell(table, "Bulan", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);
		insertCell(table, "Tgl Bayar", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);
		insertCell(table, "Pelanggan", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);
		insertCell(table, "Penagih", Element.ALIGN_CENTER, 1, fontTableHeader, Rectangle.BOX);

		if (contained) {
			setContainedTable(table, pelanggan);
		} else {
			insertEmptyCell(table, 1);
		}

		paragraph.add(table);
	}
	
	private void setContainedTable(PdfPTable table, Pelanggan pelanggan) {
		List<PembayaranEntity> list = ((PelangganEntity)pelanggan).getListPembayaran();
		int i = 1;
		for (PembayaranEntity p : list) {
			String month = Month.of(i).name();
			String tanggalBayar = DateUtil.toUserString(p.getTanggalBayar(), "-");
			
			insertCell(table, month.substring(0, 3), Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
			insertCell(table, tanggalBayar, Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
			insertCell(table, "", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
			insertCell(table, "", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
			
			i++;
		}
		
		insertEmptyCell(table, i);
	}

	private void insertEmptyCell(PdfPTable table, int i) {
		for (int x = i; x <= 12; x++) {
			String month = Month.of(x).name();
			
			insertCell(table, month.substring(0, 3), Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
			insertCell(table, "", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
			insertCell(table, "", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
			insertCell(table, "", Element.ALIGN_CENTER, 1, fontTableContent, Rectangle.BOX);
		}
	}
	
	@Override
	protected void insertCell(PdfPTable table, String text, int align, int colspan, Font font, int border) {
		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setBorder(border);

		if(text.trim().equalsIgnoreCase("")){
			cell.setMinimumHeight(22f);
		}

		table.addCell(cell);
	};
	
	@Override
	protected Document newDocument() {
		return new Document(new Rectangle(297, 466), 0.1f, 0.1f, 0.1f, 0.1f);
	}
}
