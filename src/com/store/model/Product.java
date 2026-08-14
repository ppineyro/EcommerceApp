package com.store.model;

public abstract class Product implements Comparable<Product> {
	// contador que se incrementa solito
	private static int idCounter = 1;
	
	private int id;
	private String name;
	protected double basePrice;
	private String category;
}
