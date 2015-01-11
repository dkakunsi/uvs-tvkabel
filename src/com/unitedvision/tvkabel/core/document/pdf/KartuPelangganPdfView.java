package com.unitedvision.tvkabel.core.document.pdf;

import java.time.Month;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.domain.Pelanggan;
import com.unitedvision.tvkabel.domain.Pembayaran;
import com.unitedvision.tvkabel.domain.Perusahaan;
import com.unitedvision.tvkabel.util.DateUtil;

public class KartuPelangganPdfView extends CustomAbstractPdfView {
	private Perusahaan perusahaan;
	private List<Pelanggan> listPelanggan;
	private boolean contained;
	private float[] columnWidths = {4f, 4f, 4f, 4f};
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setPelanggan((List<Pelanggan>)model.get("pelanggan"));
		setContained((int)model.get("pembayaran"));

		decorateDocument(doc, String.format("Kartu Pelanggan %s", perusahaan.getNama()));

		for (Pelanggan pelanggan : listPelanggan) {
			createPage(doc, pelanggan);
		}
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
	
	public void setPelanggan(List<Pelanggan> listPelanggan) {
		this.listPelanggan = listPelanggan;
		this.perusahaan = this.listPelanggan.get(0).getPerusahaan();
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
		paragraph.add(new Paragraph(String.format("TVK. %s", perusahaan.getNama()), new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.add(new Paragraph("Kartu Pembayaran Pelanggan", new Font(Font.TIMES_ROMAN, 14)));
		paragraph.setAlignment(Element.ALIGN_CENTER);
	}
	
	private void createHeadTable(Paragraph paragraph, Pelanggan pelanggan) throws DocumentException {
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);
		
		insertCell(table, "Kode", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getKode(), align, 3, fontContent, Rectangle.NO_BORDER);
		insertCell(table, "Nama", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, pelanggan.getNama(), align, 3, fontContent, Rectangle.NO_BORDER);
		insertCell(table, "Kontak", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, 
				String.format("%s, %s, %s", pelanggan.getTelepon(), pelanggan.getHp(), pelanggan.getEmail()), 
				align, 3, fontContent, Rectangle.NO_BORDER);
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
		insertCell(table, "Tahun", align, 1, fontHeader, Rectangle.NO_BORDER);
		insertCell(table, 
				Integer.toString(DateUtil.getYear(DateUtil.getNow())), 
				align, 1, fontContent, Rectangle.NO_BORDER);

		paragraph.add(table);
		addEmptyLine(paragraph, 1);
	}

	private void createPembayaranTable(Paragraph paragraph, Pelanggan pelanggan) {
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);
		
		insertCell(table, "Bulan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Tgl Bayar", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Penagih", align, 1, fontHeader, Rectangle.BOX);

		if (contained) {
			setContainedTable(table, align, fontHeader, fontContent, pelanggan);
		} else {
			insertEmptyCell(table, align, fontContent, 1);
		}

		paragraph.add(table);
	}
	
	private void setContainedTable(PdfPTable table, int align, Font fontHeader, Font fontContent, Pelanggan pelanggan) {
		List<Pembayaran> list = ((Pelanggan)pelanggan).getListPembayaran();
		int i = 1;
		for (Pembayaran pembayaran : list) {
			String month = Month.of(i).name();
			String tanggalBayar = DateUtil.toUserString(pembayaran.getTanggalBayar(), "-");
			
			insertCell(table, month.substring(0, 3), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, tanggalBayar, align, 1, fontContent, Rectangle.BOX);
			insertCell(table, "", align, 1, fontContent, Rectangle.BOX);
			insertCell(table, "", align, 1, fontContent, Rectangle.BOX);
			
			i++;
		}
		
		insertEmptyCell(table, align, fontContent, i);
	}

	private void insertEmptyCell(PdfPTable table, int align, Font fontContent, int i) {
		for (int x = i; x <= 12; x++) {
			String month = Month.of(x).name();
			
			insertCell(table, month.substring(0, 3), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, "", align, 1, fontContent, Rectangle.BOX);
			insertCell(table, "", align, 1, fontContent, Rectangle.BOX);
			insertCell(table, "", align, 1, fontContent, Rectangle.BOX);
		}
	}
	
	@Override
	protected Document newDocument() {
		return new Document(PageSize.A6, 0.5f, 0.5f, 0.5f, 0.5f);
	}
}
