package com.unitedvision.tvkabel.exception;

public class ApplicationException extends Exception {
	private static final long serialVersionUID = 1L;
	public static final int ID = 1;

	public ApplicationException() {
		super();
	}
	
	public ApplicationException(String message) {
		super(message);
	}
	
	public int getId() {
		return ID;
	}
}
