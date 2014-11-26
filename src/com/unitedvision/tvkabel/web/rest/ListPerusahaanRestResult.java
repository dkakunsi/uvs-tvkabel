package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.web.model.PerusahaanModel;

public class ListPerusahaanRestResult extends ListRestResult {
	private List<PerusahaanModel> list;

	protected ListPerusahaanRestResult(String message) {
		super(message);
	}
	
	private ListPerusahaanRestResult (String message, List<PerusahaanModel> list) {
		super(message, 0, 0, 0);
		this.list = list;
	}

	public List<PerusahaanModel> getListModel() {
		return list;
	}
	
	public static ListPerusahaanRestResult create(String message) {
		return new ListPerusahaanRestResult(message);
	}
	
	public static ListPerusahaanRestResult create(String message, List<PerusahaanModel> list) {
		return new ListPerusahaanRestResult(message, list);
	}
}
