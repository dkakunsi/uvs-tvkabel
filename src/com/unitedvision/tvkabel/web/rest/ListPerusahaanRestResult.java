package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.persistence.domain.Perusahaan;

public class ListPerusahaanRestResult extends ListRestResult {
	private List<Perusahaan> list;

	protected ListPerusahaanRestResult(String message) {
		super(message);
	}
	
	private ListPerusahaanRestResult (String message, List<Perusahaan> list) {
		super(message, 0, 0, 0);
		this.list = list;
	}

	public List<Perusahaan> getList() {
		return list;
	}
	
	public static ListPerusahaanRestResult create(String message) {
		return new ListPerusahaanRestResult(message);
	}
	
	public static ListPerusahaanRestResult create(String message, List<Perusahaan> list) {
		return new ListPerusahaanRestResult(message, list);
	}
}
