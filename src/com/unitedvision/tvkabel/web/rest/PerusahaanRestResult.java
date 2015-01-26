package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.persistence.entity.Perusahaan;

public class PerusahaanRestResult extends RestResult {
	private Perusahaan perusahaan;

	private PerusahaanRestResult(String message) {
		super(message);
	}
	
	private PerusahaanRestResult(String message, Perusahaan perusahaan) {
		super(message);
		this.type = "model";
		this.perusahaan = perusahaan;
	}

	public Perusahaan getModel() {
		return perusahaan;
	}
	
	public static PerusahaanRestResult create(String message) {
		return new PerusahaanRestResult(message);
	}
	
	public static PerusahaanRestResult create(String message, Perusahaan perusahaan) {
		return new PerusahaanRestResult(message, perusahaan);
	}
}
