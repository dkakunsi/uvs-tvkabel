package com.unitedvision.tvkabel.exception;

public class EntityNotExistException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public static final int ID = 6;

	public EntityNotExistException() {
		super();
	}

	public EntityNotExistException(String message) {
		super(message);
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
