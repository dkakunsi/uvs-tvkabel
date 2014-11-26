package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.web.model.PegawaiModel;

public class PegawaiRestResult extends RestResult {
	private PegawaiModel pegawaiModel;

	private PegawaiRestResult(String message) {
		super(message);
	}
	
	private PegawaiRestResult(String message, PegawaiModel pegawaiModel) {
		super(message);
		this.type = "model";
		this.pegawaiModel = pegawaiModel;
	}
	
	public PegawaiModel getModel() {
		return pegawaiModel;
	}

	public static PegawaiRestResult create(String message) {
		return new PegawaiRestResult(message);
	}
	
	public static PegawaiRestResult create(String message, PegawaiModel pegawaiModel) {
		return new PegawaiRestResult(message, pegawaiModel);
	}
}
