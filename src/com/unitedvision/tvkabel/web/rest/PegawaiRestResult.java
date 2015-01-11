package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.domain.entity.Pegawai;

public class PegawaiRestResult extends RestResult {
	private Pegawai pegawai;

	private PegawaiRestResult(String message) {
		super(message);
	}
	
	private PegawaiRestResult(String message, Pegawai pegawai) {
		super(message);
		this.type = "model";
		this.pegawai = pegawai;
	}
	
	public Pegawai get() {
		return pegawai;
	}

	public static PegawaiRestResult create(String message) {
		return new PegawaiRestResult(message);
	}
	
	public static PegawaiRestResult create(String message, Pegawai pegawai) {
		return new PegawaiRestResult(message, pegawai);
	}
}
