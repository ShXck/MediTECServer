package com.meditec.medmanagement;

import com.meditec.utilities.IdentifiersGenerator;

public class MedicTest implements Comparable<MedicTest>{
	
	int id;
	
	public MedicTest() {
		this.id = IdentifiersGenerator.generate_new_key(3);
	}
	
	public int id(){
		return id;
	}

	@Override
	public int compareTo(MedicTest o) {
		if (o.id > id) {
			return 1;
		}else {
			return -1;
		}
	}

}
