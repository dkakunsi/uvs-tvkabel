package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.web.model.PembayaranModel;

public class PembayaranRestResult extends RestResult {
	private PembayaranModel model;

	private PembayaranRestResult(String message) {
		super(message);
	}

	private PembayaranRestResult(String message, PembayaranModel model) {
		super(message);
		this.model = model;
	}
	
	public PembayaranModel getModel() {
		return model;
	}

	public static PembayaranRestResult create(String message) {
		return new PembayaranRestResult(message);
	}
	
	public static PembayaranRestResult create(String message, PembayaranModel model) {
		return new PembayaranRestResult(message, model);
	}
}
