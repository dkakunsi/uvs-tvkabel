package com.unitedvision.tvkabel.web.document.pdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.tvkabel.persistence.entity.PembayaranEntity;
import com.unitedvision.tvkabel.util.DateUtil;

public class BulanPdfView extends CustomAbstractPdfView {
	private String jenisLaporan;
	private String periode;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setJenisLaporan(model);
		setPeriode(model);
		
		decorateDocument(doc, String.format("Laporan %s Periode %s", jenisLaporan, periode));
		
		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createTable(model, paragraph);
		doc.add(paragraph);
	}
	
	public void setJenisLaporan(String jenisLaporan) {
		this.jenisLaporan = jenisLaporan;
	}
	
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	
	public void setJenisLaporan(Map<String, Object> model) {
		String jenis = (String)model.get("jenis");

		if (jenis.equals("tagihan")) {
			setJenisLaporan("Tagihan");
		} else {
			setJenisLaporan("Pembayaran");
		}
	}
	
	public void setPeriode(Map<String, Object> model) {
		String bulan = (String)model.get("bulan");
		String tahun = ((Integer)model.get("tahun")).toString();
		
		setPeriode(String.format("%s %s", bulan, tahun));
	}

	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.add(new Paragraph("Laporan Pembayaran", fontTitle));
		paragraph.add(new Paragraph(String.format("Jenis Laporan : %s / Periode : %s", jenisLaporan, periode), fontSubTitle));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(paragraph, 1);
	}
	
	private void createTable(Map<String, Object> model, Paragraph paragraph) throws DocumentException {
		float[] columnWidths = {5f, 5f, 4f, 3f, 3f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);
		
		insertCell(table, "Kode Referensi", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Penagih", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Tanggal", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Jumlah", align, 1, fontHeader, Rectangle.BOX);
		table.setHeaderRows(1);

		@SuppressWarnings("unchecked")
		final List<PembayaranEntity> list = (List<PembayaranEntity>)model.get("listPembayaran");
		long total = 0;
		for (PembayaranEntity p : list) {
			insertCell(table, p.getKode(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, p.getPelanggan().getNama(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, p.getPegawai().getNama(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, DateUtil.toString(p.getTanggalBayar()), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, Long.toString(p.getJumlahBayar()), align, 1, fontContent, Rectangle.BOX);
			
			total += p.getJumlahBayar();
		}

		insertCell(table, "Jumlah Pembayaran", Element.ALIGN_RIGHT, 4, fontContent, Rectangle.BOX);
		insertCell(table, Integer.toString(list.size()), align, 1, fontContent, Rectangle.BOX);
		insertCell(table, "Total Pembayaran", Element.ALIGN_RIGHT, 4, fontContent, Rectangle.BOX);
		insertCell(table, Long.toString(total), align, 1, fontContent, Rectangle.BOX);
		
		paragraph.add(table);
	}

	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(), 0.1f, 0.1f, 1f, 0.1f);
	}
}
