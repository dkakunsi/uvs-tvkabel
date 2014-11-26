package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.web.model.PerusahaanModel;

public class PerusahaanRestResult extends RestResult {
	private PerusahaanModel model;

	private PerusahaanRestResult(String message) {
		super(message);
	}
	
	private PerusahaanRestResult(String message, PerusahaanModel model) {
		super(message);
		this.type = "model";
		this.model = model;
	}

	public PerusahaanModel getModel() {
		return model;
	}
	
	public static PerusahaanRestResult create(String message) {
		return new PerusahaanRestResult(message);
	}
	
	public static PerusahaanRestResult create(String message, PerusahaanModel model) {
		return new PerusahaanRestResult(message, model);
	}
}
