package com.unitedvision.tvkabel.core.document.pdf;

import java.util.Date;
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
import com.unitedvision.tvkabel.core.document.pdf.CustomAbstractPdfView;
import com.unitedvision.tvkabel.persistence.entity.Pelanggan;
import com.unitedvision.tvkabel.persistence.entity.Pembayaran;
import com.unitedvision.tvkabel.util.DateUtil;

public class HariPdfView extends CustomAbstractPdfView {
	private String tanggalAwalStr;
	private String tanggalAkhirStr;
	private String namaPegawai;
	
	public void setTanggalAwalStr(String tanggal) {
		this.tanggalAwalStr = tanggal;
	}
	
	public void setTanggalAwal(Date date) {
		setTanggalAwalStr(DateUtil.toUserString(date, "-"));
	}
	
	public void setTanggalAkhirStr(String tanggal) {
		this.tanggalAkhirStr = tanggal;
	}
	
	public void setTanggalAkhir(Date date) {
		setTanggalAkhirStr(DateUtil.toUserString(date, "-"));
	}
	
	public void setNamaPegawai(String namaPegawai) {
		this.namaPegawai = namaPegawai;
	}
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		create(model, doc);
	}
	
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		setNamaPegawai((String)model.get("pegawai"));
		setTanggalAwal(DateUtil.getDate((String)model.get("tanggalAwal")));
		setTanggalAkhir(DateUtil.getDate((String)model.get("tanggalAkhir")));

		decorateDocument(doc, String.format("Laporan Pembayaran Tanggal %s", tanggalAwalStr));
		
		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createTable(model, paragraph);
		doc.add(paragraph);
		
		return doc;
	}
	
	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.add(new Paragraph("Laporan Pembayaran Harian", fontTitle));
		paragraph.add(new Paragraph(String.format("%s s/d %s", tanggalAwalStr, tanggalAkhirStr), fontSubTitle));
		paragraph.add(new Paragraph(String.format("Pegawai : %s", namaPegawai), fontSubTitle));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(paragraph, 1);
	}
	
	@SuppressWarnings("unchecked")
	protected void createTable(Map<String, Object> model, Paragraph paragraph) throws DocumentException {
		float[] columnWidths = {5f, 8f, 4f, 2f, 2f, 4f, 2f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "Kode Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Nama Pelanggan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Kelurahan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Lingk.", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Iuran", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Jumlah Bulan", align, 1, fontHeader, Rectangle.BOX);
		insertCell(table, "Total", align, 1, fontHeader, Rectangle.BOX);
		table.setHeaderRows(1);

		final List<Pelanggan> list = (List<Pelanggan>)model.get("rekap");
		long total = 0;
		for (Pelanggan pelanggan : list) {
			Font customFont = getCustomFont(pelanggan.getTunggakan());
			
			insertCell(table, pelanggan.getKode(), align, 1, customFont, Rectangle.BOX);
			insertCell(table, pelanggan.getNama(), align, 1, customFont, Rectangle.BOX);
			insertCell(table, pelanggan.getNamaKelurahan(), align, 1, customFont, Rectangle.BOX);
			insertCell(table, Integer.toString(pelanggan.getLingkungan()), align, 1, customFont, Rectangle.BOX);
			insertCell(table, Long.toString(pelanggan.getIuran()), align, 1, customFont, Rectangle.BOX);
			
			int count = 0;
			long sum = 0;
			for (Pembayaran pembayaran : pelanggan.getListPembayaran()) {
				count++;
				sum += pembayaran.getJumlahBayar();
			}
			insertCell(table, String.format("%d bulan", count), align, 1, customFont, Rectangle.BOX);
			insertCell(table, String.format("Rp %d", sum), align, 1, customFont, Rectangle.BOX);
			total += sum;
		}

		insertCell(table, "Jumlah Pelanggan", Element.ALIGN_RIGHT, 6, fontContent, Rectangle.BOX);
		insertCell(table, Integer.toString(list.size()), align, 1, fontContent, Rectangle.BOX);
		insertCell(table, "Total Pembayaran", Element.ALIGN_RIGHT, 6, fontContent, Rectangle.BOX);
		insertCell(table, Long.toString(total), align, 1, fontContent, Rectangle.BOX);
		
		paragraph.add(table);
	}

	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(), 0.5f, 0.5f, 0.5f, 0.5f);
	}
}
