package com.store.model;

import java.util.List;
import java.util.ArrayList;
import com.store.exceptions.InsufficientStockException;
import com.store.patterns.DiscountStrategy;

public class ShoppingCart {
	private List<CartItem> items;
	private DiscountStrategy discountStrategy;
	
	public ShoppingCart() {
		this.items = new ArrayList<>();
	}
	
	public void addItem(Product product, int quantity) {
		items.add(new CartItem(product, quantity));
	}
	
	public void removeItem(Product product) {
		items.removeIf(item -> item.getProduct().getId() == product.getId());
	}
	
	public double getSubtotal() {
		double subtotal = 0;
		for (CartItem item : items) {
			subtotal += item.getItemTotal();
		}
		return subtotal;
	}
	
	public double getTotal() {
		double subtotal = getSubtotal();
		if (discountStrategy != null) {
			return discountStrategy.applyDiscount(subtotal);
		}
		return subtotal;
	}
	
	// aqui va la validacion del stock donde queria implementar la excepcion que hice
	public void validateStock(int availableStock, CartItem item) throws InsufficientStockException {
		if (item.getQuantity() > availableStock) {
			throw new InsufficientStockException("No existen suficientes del producto " + item.getProduct().getName() + ". Se solicitaron" + item.getQuantity() + ". Hay disponibles " + availableStock);
		}
	}
	
	public void clear() {
		items.clear();
	}
	
	public List<CartItem> getItems() {
		return items;
	}
	public void setDiscountStrategy(DiscountStrategy discountStrategy) {
		this.discountStrategy = discountStrategy;
	}
}
