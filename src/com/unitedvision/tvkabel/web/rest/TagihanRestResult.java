package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.persistence.entity.Pembayaran.Tagihan;

public class TagihanRestResult extends RestResult {
	private Tagihan tagihan;

	private TagihanRestResult(String message) {
		super(message);
	}
	
	private TagihanRestResult(String message, Tagihan tagihan) {
		super(message);
		this.type = "model";
		this.tagihan = tagihan;
	}
	
	public Tagihan getModel() {
		return tagihan;
	}

	public static TagihanRestResult create(String message) {
		return new TagihanRestResult(message);
	}
	
	public static TagihanRestResult create(String message, Tagihan tagihan) {
		return new TagihanRestResult(message, tagihan);
	}
}
