package com.unitedvision.tvkabel.exception;

public class NotPayableCustomerException extends PaymentException {
	private static final long serialVersionUID = 1L;
	public static final int ID = 7;

	public NotPayableCustomerException() {
		super();
	}
	
	public NotPayableCustomerException(String message) {
		super(message);
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
