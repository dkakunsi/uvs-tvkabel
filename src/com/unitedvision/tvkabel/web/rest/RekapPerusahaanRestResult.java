package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.domain.entity.Perusahaan;

public class RekapPerusahaanRestResult extends RestResult {
	private Perusahaan.Rekap rekap;

	protected RekapPerusahaanRestResult(String message) {
		super(message);
	}
	
	private RekapPerusahaanRestResult(String message, Perusahaan.Rekap rekap) {
		super(message);
		this.rekap = rekap;
	}
	
	public Perusahaan.Rekap getRekap() {
		return rekap;
	}

	public static RekapPerusahaanRestResult create(String message) {
		return new RekapPerusahaanRestResult(message);
	}
	
	public static RekapPerusahaanRestResult create(String message, Perusahaan.Rekap rekap) {
		return new RekapPerusahaanRestResult(message, rekap);
	}
}
