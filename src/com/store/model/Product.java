package com.store.model;

public abstract class Product implements Comparable<Product> {
	// contador que se incrementa solito
	private static int idCounter = 1;
	
	private int id;
	private String name;
	protected double basePrice;
	private String category;
	
	// constructor normal y basico
	public Product(String name, double basePrice) {
		this.id = idCounter++;
		this.name = name;
		setBasePrice(basePrice);
		this.category = "General";
	}
	
	// constructor sobrecargado
	public Product(String name, double basePrice, String category) {
		this.id = idCounter++;
		this.name = name;
		setBasePrice(basePrice);
		this.category = category;
	}
	
	// metodo abstracto
	public abstract double calculateFinalPrice();
	
	// ordenamiento de los prorductos
	@Override
	public int compareTo(Product other) {
		return Double.compare(this.basePrice, other.basePrice);
	}
	
	// getters y setters
	public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice <= 0) {
            throw new IllegalArgumentException("El precio base debe ser mayor que cero.");
        }
        this.basePrice = basePrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
