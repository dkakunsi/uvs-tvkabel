package com.unitedvision.tvkabel.exception;

public class StatusChangeException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public static final int ID = 8;

	public StatusChangeException() {
		super();
	}

	public StatusChangeException(String message) {
		super(message);
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
