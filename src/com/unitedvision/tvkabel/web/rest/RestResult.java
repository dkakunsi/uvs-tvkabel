package com.unitedvision.tvkabel.web.rest;

public class RestResult {
	protected String type;
	protected String message;

	protected RestResult(String message) {
		super();
		this.type = "message";
		this.message = message;
	}

	public String getType() {
		return type;
	}
	
	public String getMessage() {
		return message;
	}
	
	public static RestResult create(String message) {
		return new RestResult(message);
	}
}
