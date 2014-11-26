package com.unitedvision.tvkabel.web.rest;

import com.unitedvision.tvkabel.web.model.TagihanModel;

public class TagihanRestResult extends RestResult {
	private TagihanModel model;

	private TagihanRestResult(String message) {
		super(message);
	}
	
	private TagihanRestResult(String message, TagihanModel model) {
		super(message);
		this.type = "model";
		this.model = model;
	}
	
	public TagihanModel getModel() {
		return model;
	}

	public static TagihanRestResult create(String message) {
		return new TagihanRestResult(message);
	}
	
	public static TagihanRestResult create(String message, TagihanModel model) {
		return new TagihanRestResult(message, model);
	}
}
