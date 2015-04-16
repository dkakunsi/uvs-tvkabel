package com.unitedvision.tvkabel.document.pdf;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.unitedvision.tvkabel.entity.Pelanggan;
import com.unitedvision.tvkabel.util.DateUtil;

public class BulanPdfView extends HariPdfView {
	private String periode;
	private Month month;
	private int tahun;
	
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	
	public void setMonth(String month) {
		setMonth(Month.valueOf(month));
	}
	
	public void setMonth(Month month) {
		this.month = month;
	}

	public void setTahun(int tahun) {
		this.tahun = tahun;
	}

	public void setPeriode(Map<String, Object> model) {
		String bulan = (String)model.get("bulan");
		int tahun = (Integer)model.get("tahun");
		
		setMonth(bulan);
		setTahun(tahun);
		setPeriode(String.format("%s %s", bulan, tahun));
	}
	
	@SuppressWarnings("unchecked")
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		setPeriode(model);
		
		decorateDocument(doc, String.format("Laporan Bulan %s", periode));
		
		Paragraph paragraph = new Paragraph();
		createTitle(paragraph);
		createContent(paragraph, (List<Pelanggan>)model.get("rekap"));
		doc.add(paragraph);
		
		return doc;
	}

	protected void createTitle(Paragraph paragraph) throws DocumentException {
		paragraph.add(new Paragraph("Laporan Pembayaran", fontTitle));
		paragraph.add(new Paragraph(String.format("Laporan Bulanan Periode : %s", periode), fontSubTitle));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(paragraph, 1);
	}
	
	protected boolean checkTanggalBayar(Date tanggal) {
		if (DateUtil.getYear(tanggal) != tahun)
			return false;
		if (DateUtil.getMonth(tanggal) != month)
			return false;
		return true;
	}
	
	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(), 0.1f, 0.1f, 1f, 0.1f);
	}
}
