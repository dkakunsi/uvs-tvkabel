package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.web.model.KotaModel;

public class ListKotaRestResult extends ListRestResult {
	private List<KotaModel> list;

	protected ListKotaRestResult(String message) {
		super(message);
	}
	
	private ListKotaRestResult(String message, List<KotaModel> list) {
		super(message, 0, 0, 0);
		this.list = list;
	}

	public List<KotaModel> getListModel() {
		return list;
	}
	
	public static ListKotaRestResult create(String message) {
		return new ListKotaRestResult(message);
	}
	
	public static ListKotaRestResult create(String message, List<KotaModel> list) {
		return new ListKotaRestResult(message, list);
	}
}
