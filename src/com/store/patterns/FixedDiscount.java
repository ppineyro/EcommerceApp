package com.store.patterns;

public class FixedDiscount implements DiscountStrategy {
	private double discountAmount;
	
	public FixedDiscount(double discountAmount) {
		if (discountAmount<0) {
			throw new IllegalArgumentException("La cantidad de descuento no puede ser negativa!");
		}
		this.discountAmount = discountAmount;
	}
	
	@Override
	public double applyDiscount(double total) {
		return Math.max(0,  total - discountAmount);
	}
}
