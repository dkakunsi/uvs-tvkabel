package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.web.model.KelurahanModel;

public class ListKelurahanRestResult extends ListRestResult {
	private List<KelurahanModel> list;

	private ListKelurahanRestResult(String message) {
		super(message);
	}
	
	private ListKelurahanRestResult(String message, List<KelurahanModel> list, int page, long total, long count) {
		super(message, page, total, count);
		this.list = list;
	}
	
	public List<KelurahanModel> getListModel() {
		return list;
	}
	
	public static ListKelurahanRestResult create(String message) {
		return new ListKelurahanRestResult(message);
	}
	
	public static ListKelurahanRestResult create(String message, List<KelurahanModel> list) {
		return new ListKelurahanRestResult(message, list, 0, 0, 0);
	}
}
