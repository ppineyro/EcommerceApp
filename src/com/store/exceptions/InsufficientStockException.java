package com.store.exceptions;

public class InsufficientStockException extends Exception {

	/**
	 * ID de serialización por defecto porque eclipse te lo sugiere lol
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientStockException() {
		super("No hay suficientes unidades de este producto en existencia.");
	}

	public InsufficientStockException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InsufficientStockException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InsufficientStockException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InsufficientStockException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
