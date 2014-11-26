package com.unitedvision.tvkabel.exception;

public class UnpaidBillException extends PaymentException {
	private static final long serialVersionUID = 1L;
	public static final int ID = 10;

	public UnpaidBillException() {
		super();
	}

	public UnpaidBillException(String message) {
		super(message);
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
