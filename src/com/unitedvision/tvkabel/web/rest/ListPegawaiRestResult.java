package com.unitedvision.tvkabel.web.rest;

import java.util.List;

import com.unitedvision.tvkabel.persistence.entity.Pegawai;

public class ListPegawaiRestResult extends ListRestResult {
	private List<Pegawai> list;

	private ListPegawaiRestResult(String message) {
		super(message);
	}

	private ListPegawaiRestResult(String message, List<Pegawai> list, int page, long total, long count) {
		super(message, page, total, count);
		this.list = list;
	}
	
	public List<Pegawai> getListModel() {
		return list;
	}
	
	public static ListPegawaiRestResult create(String message, List<Pegawai> list) {
		return new ListPegawaiRestResult(message, list, 0, 0, 0);
	}
	
	public static ListPegawaiRestResult create(String message, List<Pegawai> list, int page, long total, long count) {
		return new ListPegawaiRestResult(message, list, page, total, count);
	}

	public static ListPegawaiRestResult create(String message) {
		return new ListPegawaiRestResult(message);
	}
}
