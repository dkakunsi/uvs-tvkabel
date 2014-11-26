package com.unitedvision.tvkabel.exception;

public class UncompatibleTypeException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public static final int ID = 9;

	public UncompatibleTypeException() {
		super();
	}

	public UncompatibleTypeException(String message) {
		super(message);
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
