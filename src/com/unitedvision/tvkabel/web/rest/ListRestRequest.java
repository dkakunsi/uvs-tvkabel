package com.unitedvision.tvkabel.web.rest;

public class ListRestRequest extends RestRequest {
	protected int lastNumber;
	protected String submit;
	
	public ListRestRequest() {
		super();
	}
	
	public ListRestRequest(int lastNumber, String submit) {
		super();
		this.lastNumber = lastNumber;
		this.submit = submit;
	}

	public int getLastNumber() {
		return lastNumber;
	}

	public void setLastNumber(int lastNumber) {
		this.lastNumber = lastNumber;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}
}
