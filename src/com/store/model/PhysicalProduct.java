package com.store.model;

public class PhysicalProduct extends Product implements Shippable {

	public PhysicalProduct() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String generateTrackingGuide() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getShippingCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(Product o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
