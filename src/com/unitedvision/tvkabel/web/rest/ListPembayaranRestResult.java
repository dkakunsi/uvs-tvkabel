package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.persistence.domain.Pembayaran;

public class ListPembayaranRestResult extends ListRestResult {
	private List<Pembayaran> list;

	private ListPembayaranRestResult(String message) {
		super(message);
	}
	
	private ListPembayaranRestResult(String message, List<Pembayaran> list, int page, long total, long count) {
		super(message, page, total, count);
		this.list = list;
	}

	public List<Pembayaran> getList() {
		return list;
	}
	
	public static ListPembayaranRestResult create(String message) {
		return new ListPembayaranRestResult(message);
	}
	
	public static ListPembayaranRestResult create(String message, List<Pembayaran> list, int page, long total, long count) {
		return new ListPembayaranRestResult(message, list, page, total, count);
	}
}
