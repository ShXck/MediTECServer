package com.meditec.medmanagement;

import com.meditec.datastructures.List;
import com.meditec.utilities.IdentifiersGenerator;

public class ClinicCase implements Comparable<ClinicCase>{
	
	private List<Medication> medication;
	private List<MedicTest> tests;
	private int key;
	
	public ClinicCase(){
		this.medication = new List<>();
		this.tests = new List<>();
		this.key = IdentifiersGenerator.generate_new_key(3);
	}
	
	public int key() {
		return key;
	}

	@Override
	public int compareTo(ClinicCase o) {
		if (o.key() > key) {
			return 1;
		}else {
			return -1;
		}
	}

}
