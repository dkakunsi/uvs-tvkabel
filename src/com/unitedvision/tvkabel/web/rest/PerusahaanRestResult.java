package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.persistence.entity.Perusahaan;

public class PerusahaanRestResult extends RestResult {
	private Perusahaan perusahaan;

	private PerusahaanRestResult(String message) {
		super(message);
	}
	
	private PerusahaanRestResult(String message, Perusahaan model) {
		super(message);
		this.type = "model";
		this.perusahaan = model;
	}

	public Perusahaan getModel() {
		return perusahaan;
	}
	
	public static PerusahaanRestResult create(String message) {
		return new PerusahaanRestResult(message);
	}
	
	public static PerusahaanRestResult create(String message, Perusahaan model) {
		return new PerusahaanRestResult(message, model);
	}
}
