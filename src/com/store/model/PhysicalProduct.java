package com.store.model;

public class PhysicalProduct extends Product implements Shippable {
	
	private double weightKg;
	private double shippingFeePerKg;

	// este constructor le delega a la clase padre a traves del super
	public PhysicalProduct(String name, double basePrice, String category, double weightKg, double shippingFeePerKg) {
		super(name, basePrice, category);
		this.weightKg = weightKg;
		this.shippingFeePerKg = shippingFeePerKg;
	}
	
	// aqui va la implementacion del metodo abstracto de product
	@Override
	public double calculateFinalPrice() {
		return basePrice + getShippingCost();
	}
	
	@Override
	public String generateTrackingGuide() {
		return "SHIPPING-" + getId() + "-" + System.currentTimeMillis() % 10000;
	}

	@Override
	public double getShippingCost() {
		return weightKg*shippingFeePerKg;
	}
	
	// getters y setters de la subclase
    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public double getShippingFeePerKg() {
        return shippingFeePerKg;
    }

    public void setShippingFeePerKg(double shippingFeePerKg) {
        this.shippingFeePerKg = shippingFeePerKg;
    }
}
