package com.store.model;

public interface Shippable {
	String generateTrackingGuide();
	double getShippingCost();
}
