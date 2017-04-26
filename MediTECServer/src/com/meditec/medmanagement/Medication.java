package com.meditec.medmanagement;

public class Medication {
	
	private String name;
	private double price;
	
	public Medication(String name, double price){
		this.name = name;
		this.price = price;
	}
	
	public void change_price(double new_price){
		this.price = new_price;
	}
	
	public double price(){
		return price;
	}
	
	public String name(){
		return name;
	}

}
