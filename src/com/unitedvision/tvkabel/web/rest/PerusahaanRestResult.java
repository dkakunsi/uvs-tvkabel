package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.domain.entity.Perusahaan;

public class PerusahaanRestResult extends RestResult {
	private Perusahaan model;

	private PerusahaanRestResult(String message) {
		super(message);
	}
	
	private PerusahaanRestResult(String message, Perusahaan model) {
		super(message);
		this.type = "model";
		this.model = model;
	}

	public Perusahaan get() {
		return model;
	}
	
	public static PerusahaanRestResult create(String message) {
		return new PerusahaanRestResult(message);
	}
	
	public static PerusahaanRestResult create(String message, Perusahaan model) {
		return new PerusahaanRestResult(message, model);
	}
}
