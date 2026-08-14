package com.store.model;

public class CartItem {
	private Product product;
	private int quantity;
	
	public CartItem(Product product, int quantity) {
		this.product = product;
		setQuantity(quantity);
	}
	
	public double getItemTotal() {
		return product.calculateFinalPrice()*quantity;
	}

	public Product getProduct() {
		return product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		if (quantity<=0) {
			throw new IllegalArgumentException("La cantidad debe ser mayor a cero!");
		}
		this.quantity = quantity;
	}
}
