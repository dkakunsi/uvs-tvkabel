package com.unitedvision.tvkabel.exception;

public class PaymentException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public static final int ID = 2;

	public PaymentException() {
		super();
	}
	
	public PaymentException(String message) {
		super(message);
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
