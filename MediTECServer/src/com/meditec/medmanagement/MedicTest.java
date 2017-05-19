package com.meditec.medmanagement;

public class MedicTest implements Comparable<MedicTest>{
	
	int id;
	private String name;
	private int cost;
	
	public MedicTest(String name, String cost, String id) {
		this.id = Integer.parseInt(id);
		this.name = name;
		this.cost = Integer.parseInt(cost);
	}
	
	/**
	 * 
	 * @return la identificación del exámen.
	 */
	public int id(){
		return id;
	}
	
	/**
	 * el costo del exámen.
	 * @return
	 */
	public int cost(){
		return cost;
	}
	
	/**
	 * 
	 * @return el nombre.
	 */
	public String name(){
		return name;
	}
	
	/**
	 * edita el nombre del exámen.
	 * @param name el nuevo nombre.
	 */
	public void edit_name(String name){
		this.name = name;
	}
	
	/**
	 * edita el precio del exámen.
	 * @param new_price el nuevo precio.
	 */
	public void edit_price(int new_price){
		this.cost = new_price;
	}

	@Override
	public int compareTo(MedicTest o) {
		if (o.id > id) {
			return 1;
		}else if (o.id < id) {
			return -1;
		}else{
			return 0;
		}
	}

}
