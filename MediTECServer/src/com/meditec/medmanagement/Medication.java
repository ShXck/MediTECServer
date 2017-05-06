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
	
	public void edit_name(String new_name){
		this.name = new_name;
	}
	
	public void edit_cost(int new_cost){
		this.price = new_cost;
	}

	@Override
	public int compareTo(Medication o) {
		if (o.id() > id) {
			return 1;
		}else if(o.id() < id){
			return -1;
		}else{
			return 0;
		}
	}
}
