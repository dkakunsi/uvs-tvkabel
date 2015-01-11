package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.persistence.domain.Kecamatan;

public class ListKecamatanRestResult extends ListRestResult {
	private List<Kecamatan> list;

	protected ListKecamatanRestResult(String message) {
		super(message);
	}
	
	private ListKecamatanRestResult(String message, List<Kecamatan> list) {
		super(message, 0, 0, 0);
		this.list = list;
	}

	public List<Kecamatan> getList() {
		return list;
	}
	
	public static ListKecamatanRestResult create(String message) {
		return new ListKecamatanRestResult(message);
	}
	
	public static ListKecamatanRestResult create(String message, List<Kecamatan> list) {
		return new ListKecamatanRestResult(message, list);
	}
}
