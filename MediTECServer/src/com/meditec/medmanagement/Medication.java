package com.meditec.medmanagement;

public class Medication implements Comparable<Medication> {
	
	private String name;
	private double price;
	private int id;
	
	public Medication(String name, String price, String id){
		this.name = name;
		this.price = Double.parseDouble(price);
		this.id = Integer.parseInt(id);
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
	
	public int id(){
		return this.id;
	}

	@Override
	public int compareTo(Medication o) {
		if (o.price > price) {
			return 1;
		}else if(o.price < price){
			return -1;
		}else{
			return 0;
		}
	}
}
