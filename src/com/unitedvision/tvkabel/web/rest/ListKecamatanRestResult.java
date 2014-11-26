package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.web.model.KecamatanModel;

public class ListKecamatanRestResult extends ListRestResult {
	private List<KecamatanModel> list;

	protected ListKecamatanRestResult(String message) {
		super(message);
	}
	
	private ListKecamatanRestResult(String message, List<KecamatanModel> list) {
		super(message, 0, 0, 0);
		this.list = list;
	}

	public List<KecamatanModel> getListModel() {
		return list;
	}
	
	public static ListKecamatanRestResult create(String message) {
		return new ListKecamatanRestResult(message);
	}
	
	public static ListKecamatanRestResult create(String message, List<KecamatanModel> list) {
		return new ListKecamatanRestResult(message, list);
	}
}
