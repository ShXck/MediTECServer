package com.meditec.medmanagement;

import com.meditec.utilities.IdentifiersGenerator;

public class MedicTest implements Comparable<MedicTest>{
	
	int id;
	private String name;
	private int cost;
	
	public MedicTest(String name, String cost, String id) {
		this.id = Integer.parseInt(id);
		this.name = name;
		this.cost = Integer.parseInt(cost);
	}
	
	public int id(){
		return id;
	}
	
	public int cost(){
		return cost;
	}
	
	public String name(){
		return name;
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
