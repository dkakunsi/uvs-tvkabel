package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.web.model.PelangganModel;

public class ListPelangganRestResult extends ListRestResult {
	private List<PelangganModel> list;

	protected ListPelangganRestResult(String message) {
		super(message);
	}
	
	private ListPelangganRestResult(String message, List<PelangganModel> list, int page, long total, long count) {
		super(message, page, total, count);
		this.list = list;
	}
	
	public List<PelangganModel> getListModel() {
		return list;
	}
	
	public static ListPelangganRestResult create(String message, List<PelangganModel> list) {
		return new ListPelangganRestResult(message, list, 0, 0, 0);
	}
	
	public static ListPelangganRestResult create(String message, List<PelangganModel> list, int page, long total, long count) {
		return new ListPelangganRestResult(message, list, page, total, count);
	}
}
