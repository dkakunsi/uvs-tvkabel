package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.persistence.entity.Pembayaran.Tagihan;

public class TagihanRestResult extends RestResult {
	private Tagihan model;

	private TagihanRestResult(String message) {
		super(message);
	}
	
	private TagihanRestResult(String message, Tagihan model) {
		super(message);
		this.type = "model";
		this.model = model;
	}
	
	public Tagihan get() {
		return model;
	}

	public static TagihanRestResult create(String message) {
		return new TagihanRestResult(message);
	}
	
	public static TagihanRestResult create(String message, Tagihan model) {
		return new TagihanRestResult(message, model);
	}
}
