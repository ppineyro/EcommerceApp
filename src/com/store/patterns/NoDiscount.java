package com.store.patterns;

public class NoDiscount implements DiscountStrategy {
	
	@Override
    public double applyDiscount(double total) {
        return total;
    }
}
