package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.domain.Pelanggan;

public class PelangganRestResult extends RestResult {
	private Pelanggan pelanggan;

	private PelangganRestResult(String message) {
		super(message);
	}
	
	private PelangganRestResult(String message, Pelanggan pelanggan) {
		super(message);
		this.type = "model";
		this.pelanggan = pelanggan;
	}
	
	public Pelanggan getModel() {
		return pelanggan;
	}

	public static PelangganRestResult create(String message) {
		return new PelangganRestResult(message);
	}
	
	public static PelangganRestResult create(String message, Pelanggan pelanggan) {
		return new PelangganRestResult(message, pelanggan);
	}
}
