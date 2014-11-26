package com.unitedvision.tvkabel.exception;

public class EmptyIdException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public static final int ID = 5;

	public EmptyIdException() {
		super();
	}
	
	public EmptyIdException(String message) {
		super(message);
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
