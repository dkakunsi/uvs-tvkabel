package com.unitedvision.tvkabel.exception;

public class DataDuplicationException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public static final int ID = 3;

	public DataDuplicationException() {
		super();
	}

	public DataDuplicationException(String message) {
		super(message);
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
