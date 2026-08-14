package com.store.model;

public class DigitalProduct extends Product {
	
	private double fileSizeMB;
	private String downloadUrl;

	public DigitalProduct(String name, double basePrice, String category, double fileSizeMB, String downloadUrl) {
        super(name, basePrice, category);
        this.fileSizeMB = fileSizeMB;
        this.downloadUrl = downloadUrl;
    }

	// aqui va la implementacion del metodo abstracto de product
	@Override
	public double calculateFinalPrice() {
		return basePrice;
	}
	
	public String generateDownloadLink() {
		return "https://tiendadementiritas.com/downloads/" + downloadUrl + "?id=" + getId();
	}

	// getters y setters de la subclase
    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public void setFileSizeMB(double fileSizeMB) {
        this.fileSizeMB = fileSizeMB;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
