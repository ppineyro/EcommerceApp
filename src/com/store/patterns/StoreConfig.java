package com.store.patterns;

public class StoreConfig {
	
	private static StoreConfig instance;
	
	private String storeName;
	private double taxRate;
	private String currencySymbol;
	
	// constructor para que no se pueda hacer un new storeconfig fuera de esta clase
	private StoreConfig() {
		this.storeName = "VendeLoQueSea.com";
		this.taxRate = 0.16;
		this.currencySymbol = "$";
	}
	
	// el getter estatico que mencione en el readme que es para acceder globalmente
	public static StoreConfig getInstance() {
		if (instance == null) {
			instance= new StoreConfig();
		}
		return instance;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
	
}
