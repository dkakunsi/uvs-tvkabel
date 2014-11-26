package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.web.model.PegawaiModel;

public class ListPegawaiRestResult extends ListRestResult {
	private List<PegawaiModel> list;

	private ListPegawaiRestResult(String message) {
		super(message);
	}

	private ListPegawaiRestResult(String message, List<PegawaiModel> list, int page, long total, long count) {
		super(message, page, total, count);
		this.list = list;
	}
	
	public List<PegawaiModel> getListModel() {
		return list;
	}
	
	public static ListPegawaiRestResult create(String message, List<PegawaiModel> list) {
		return new ListPegawaiRestResult(message, list, 0, 0, 0);
	}
	
	public static ListPegawaiRestResult create(String message, List<PegawaiModel> list, int page, long total, long count) {
		return new ListPegawaiRestResult(message, list, page, total, count);
	}
}
