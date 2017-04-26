package com.meditec.medmanagement;

import com.meditec.datastructures.List;

public class ClinicCase {
	
	private List<Medication> medication;
	private List<MedicTest> tests;
	
	public ClinicCase(){
		this.medication = new List<>();
		this.tests = new List<>();
	}

}
