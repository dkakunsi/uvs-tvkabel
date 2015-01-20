package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.persistence.entity.Kota;

public class ListKotaRestResult extends ListRestResult {
	private List<Kota> list;

	protected ListKotaRestResult(String message) {
		super(message);
	}
	
	private ListKotaRestResult(String message, List<Kota> list) {
		super(message, 0, 0, 0);
		this.list = list;
	}

	public List<Kota> getListModel() {
		return list;
	}
	
	public static ListKotaRestResult create(String message) {
		return new ListKotaRestResult(message);
	}
	
	public static ListKotaRestResult create(String message, List<Kota> list) {
		return new ListKotaRestResult(message, list);
	}
}
