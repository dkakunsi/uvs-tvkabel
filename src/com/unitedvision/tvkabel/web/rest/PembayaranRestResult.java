package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.domain.Pembayaran;

public class PembayaranRestResult extends RestResult {
	private Pembayaran model;

	private PembayaranRestResult(String message) {
		super(message);
	}

	private PembayaranRestResult(String message, Pembayaran model) {
		super(message);
		this.model = model;
	}
	
	public Pembayaran get() {
		return model;
	}

	public static PembayaranRestResult create(String message) {
		return new PembayaranRestResult(message);
	}
	
	public static PembayaranRestResult create(String message, Pembayaran model) {
		return new PembayaranRestResult(message, model);
	}
}
