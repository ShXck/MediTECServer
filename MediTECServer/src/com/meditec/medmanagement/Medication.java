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
	
	/**
	 * cambia el precio de un medicamento.
	 * @param new_price el nuevo precio.
	 */
	public void change_price(double new_price){
		this.price = new_price;
	}
	
	/**
	 * 
	 * @return el precio.
	 */
	public double price(){
		return price;
	}
	
	/**
	 * 
	 * @return el nombre.
	 */
	public String name(){
		return name;
	}
	
	/**
	 * 
	 * @return la identificación del medicamento.
	 */
	public int id(){
		return this.id;
	}
	
	/**
	 * edita el nombre del medicamento.
	 * @param new_name el nuevo nombre.
	 */
	public void edit_name(String new_name){
		this.name = new_name;
	}
	
	/**
	 * edita el costo del medicamento.
	 * @param new_cost el nuevo costo.
	 */
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
