package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.web.model.PerusahaanModel;

public class RekapPerusahaanRestResult extends RestResult {
	private PerusahaanModel.Rekap rekap;

	protected RekapPerusahaanRestResult(String message) {
		super(message);
	}
	
	private RekapPerusahaanRestResult(String message, PerusahaanModel.Rekap rekap) {
		super(message);
		this.rekap = rekap;
	}
	
	public PerusahaanModel.Rekap getRekap() {
		return rekap;
	}

	public static RekapPerusahaanRestResult create(String message) {
		return new RekapPerusahaanRestResult(message);
	}
	
	public static RekapPerusahaanRestResult create(String message, PerusahaanModel.Rekap rekap) {
		return new RekapPerusahaanRestResult(message, rekap);
	}
}
