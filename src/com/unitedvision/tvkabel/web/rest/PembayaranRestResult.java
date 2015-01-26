package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.persistence.entity.Pembayaran;

public class PembayaranRestResult extends RestResult {
	private Pembayaran pembayaran;

	private PembayaranRestResult(String message) {
		super(message);
	}

	private PembayaranRestResult(String message, Pembayaran pembayaran) {
		super(message);
		this.pembayaran = pembayaran;
	}
	
	public Pembayaran getModel() {
		return pembayaran;
	}

	public static PembayaranRestResult create(String message) {
		return new PembayaranRestResult(message);
	}
	
	public static PembayaranRestResult create(String message, Pembayaran pembayaran) {
		return new PembayaranRestResult(message, pembayaran);
	}
}
