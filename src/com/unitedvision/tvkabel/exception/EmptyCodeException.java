package com.unitedvision.tvkabel.exception;

public class EmptyCodeException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public static final int ID = 4;

	public EmptyCodeException() {
		super();
	}
	
	public EmptyCodeException(String message) {
		super(message);
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
