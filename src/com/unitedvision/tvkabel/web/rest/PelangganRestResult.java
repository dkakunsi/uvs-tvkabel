package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.web.model.PelangganModel;

public class PelangganRestResult extends RestResult {
	private PelangganModel pelangganModel;

	private PelangganRestResult(String message) {
		super(message);
	}
	
	private PelangganRestResult(String message, PelangganModel pelangganModel) {
		super(message);
		this.type = "model";
		this.pelangganModel = pelangganModel;
	}
	
	public PelangganModel getModel() {
		return pelangganModel;
	}

	public static PelangganRestResult create(String message) {
		return new PelangganRestResult(message);
	}
	
	public static PelangganRestResult create(String message, PelangganModel pelangganModel) {
		return new PelangganRestResult(message, pelangganModel);
	}
}
