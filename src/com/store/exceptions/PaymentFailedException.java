package com.store.exceptions;

public class PaymentFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentFailedException() {
		super("Falló su pago, por favor revise su información de pago");
	}

	public PaymentFailedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PaymentFailedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public PaymentFailedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PaymentFailedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
