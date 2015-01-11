package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.persistence.domain.Pelanggan;

public class ListPelangganRestResult extends ListRestResult {
	private List<Pelanggan> list;

	protected ListPelangganRestResult(String message) {
		super(message);
	}
	
	private ListPelangganRestResult(String message, List<Pelanggan> list, int page, long total, long count) {
		super(message, page, total, count);
		this.list = list;
	}
	
	public List<Pelanggan> getListModel() {
		return list;
	}
	
	public static ListPelangganRestResult create(String message, List<Pelanggan> list) {
		return new ListPelangganRestResult(message, list, 0, 0, 0);
	}
	
	public static ListPelangganRestResult create(String message, List<Pelanggan> list, int page, long total, long count) {
		return new ListPelangganRestResult(message, list, page, total, count);
	}
}
