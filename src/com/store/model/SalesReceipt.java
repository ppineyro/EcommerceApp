package com.store.model;

import java.util.List;
import java.util.ArrayList;

public final class SalesReceipt {
	private static int receiptCounter = 1000;
	
	private final int receiptId;
	private final Customer customer;
	private final List<CartItem> items;
	private final double totalPaid;
	private final String purchaseDate;
	
	public SalesReceipt(Customer customer, List<CartItem> items, double totalPaid) {
		this.receiptId = receiptCounter++;
		this.customer = customer;
		this.items = new ArrayList<>(items);
		this.totalPaid = totalPaid;
		this.purchaseDate = java.time.LocalDate.now().toString();
	}
	
	public int getReceiptId() {
		return receiptId;
	}
    public Customer getCustomer() {
    	return customer;
    }
    public List<CartItem> getItems() {
    	return new ArrayList<>(items);
    }
    public double getTotalPaid() {
    	return totalPaid;
    }
    public String getPurchaseDate() {
    	return purchaseDate;
    }

    public void printReceipt() {
        System.out.println("====================================");
        System.out.println("        RECIBO DE COMPRA #" + receiptId);
        System.out.println("====================================");
        System.out.println("Fecha: " + purchaseDate);
        System.out.println("Cliente: " + customer.getName() + " (" + customer.getEmail() + ")");
        System.out.println("------------------------------------");
        for (CartItem item : items) {
            System.out.println(item.getProduct().getName() + " x" + item.getQuantity() + 
                               " - $" + item.getItemTotal());
        }
        System.out.println("------------------------------------");
        System.out.println("TOTAL A PAGAR: $" + totalPaid);
        System.out.println("====================================\n");
    }
}
