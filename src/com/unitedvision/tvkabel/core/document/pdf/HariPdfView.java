package com.unitedvision.tvkabel.core.document.pdf;

import java.time.Month;
import java.util.Date;
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
import com.unitedvision.tvkabel.persistence.domain.Pelanggan;
import com.unitedvision.tvkabel.persistence.domain.Pembayaran;
import com.unitedvision.tvkabel.util.DateUtil;

public class HariPdfView extends CustomAbstractPdfView {
	private String tanggal;
	private String namaPegawai;
	private Date date;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setNamaPegawai((String)model.get("pegawai"));
		setTanggal(DateUtil.getDate((String)model.get("tanggal")));

		decorateDocument(doc, String.format("Laporan Pembayaran Tanggal %s", tanggal));
		
		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createTable(model, paragraph);
		doc.add(paragraph);
	}
	
	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}
	
	public void setTanggal(Date date) {
		this.date = date;
		setTanggal(DateUtil.toUserString(date, "-"));
	}
	
	public void setNamaPegawai(String namaPegawai) {
		this.namaPegawai = namaPegawai;
	}
	
	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.add(new Paragraph("Laporan Pembayaran Harian", fontTitle));
		paragraph.add(new Paragraph(String.format("Tanggal : %s", tanggal), fontSubTitle));
		paragraph.add(new Paragraph(String.format("Pegawai : %s", namaPegawai), fontSubTitle));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(paragraph, 1);
	}
	
	@SuppressWarnings("unchecked")
	private void createTable(Map<String, Object> model, Paragraph paragraph) throws DocumentException {
		float[] columnWidths = {5f, 8f, 4f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);
		
		insertCell(table, "Kode Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Nama Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Kelurahan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Lingk.", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Iuran", align, 1, fontHeader, Rectangle.BOX);
		
		for (Month m : (List<Month>)model.get("listBulan")) {
			String str = m.name().substring(0, 3);
			insertCell(table, str, align, 1, fontHeader, Rectangle.BOX);
		}
		table.setHeaderRows(1);

		final List<Pelanggan> list = (List<Pelanggan>)model.get("rekap");
		long total = 0;
		for (Pelanggan pelanggan : list) {
			insertCell(table, pelanggan.getKode(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, pelanggan.getNama(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, pelanggan.getNamaKelurahan(), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, Integer.toString(pelanggan.getLingkungan()), align, 1, fontContent, Rectangle.BOX);
			insertCell(table, Long.toString(pelanggan.getIuran()), align, 1, fontContent, Rectangle.BOX);
			for (Pembayaran pembayaran : pelanggan.getListPembayaran()) {
				if (pembayaran.getJumlahBayar() == 0) {
					insertCell(table, "", align, 1, fontContent, Rectangle.BOX);
				} else {
					insertCell(table, "lunas", align, 1, fontContent, Rectangle.BOX);

					if (DateUtil.equals(pembayaran.getTanggalBayar(), date))
						total += pembayaran.getJumlahBayar();
				}
			}
		}

		insertCell(table, "Jumlah Pembayaran", Element.ALIGN_RIGHT, 9, fontContent, Rectangle.BOX);
		insertCell(table, Integer.toString(list.size()), align, 1, fontContent, Rectangle.BOX);
		insertCell(table, "Total Pembayaran", Element.ALIGN_RIGHT, 9, fontContent, Rectangle.BOX);
		insertCell(table, Long.toString(total), align, 1, fontContent, Rectangle.BOX);
		
		paragraph.add(table);
	}

	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(), 0.5f, 0.5f, 0.5f, 0.5f);
	}
}
