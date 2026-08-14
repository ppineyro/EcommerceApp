package com.store.model;

public class Customer {
	
	private static int idCounter = 1;
	private int id;
	private String name;
	private String email;
	private Address address;
	
	public Customer(String name, String email, Address address) {
		this.id = idCounter++;
		this.name = name;
		this.email = email;
		this.address = address;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
    public String toString() {
        return "Cliente #" + id + ": " + name + " (" + email + ") - Dirección: [" + address + "]";
    }
	
}
