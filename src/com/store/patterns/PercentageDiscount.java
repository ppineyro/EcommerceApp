package com.store.patterns;

public class PercentageDiscount implements DiscountStrategy {
	private double percentage;
	
	public PercentageDiscount(double percentage) {
		if (percentage<0 || percentage>100) {
			throw new IllegalArgumentException("El porcentaje debe ser entre 0 y 100!");
		}
		this.percentage = percentage;
	}
	
	@Override
	public double applyDiscount(double total) {
		return total - (total*(percentage/100.0));
	}
}
