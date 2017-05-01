package com.meditec.medmanagement;

import com.meditec.clientmanagement.Patient;
import com.meditec.datastructures.AVLNode;
import com.meditec.datastructures.AVLTree;
import com.meditec.datastructures.SplayTree;
import com.meditec.utilities.IdentifiersGenerator;

public class ClinicCase implements Comparable<ClinicCase>{
	
	private AVLTree<Medication> medication;
	private SplayTree<MedicTest> tests;
	private int key;
	
	public ClinicCase(AVLTree<Medication> medication, SplayTree<MedicTest> tests){
		this.medication = medication;
		this.tests = tests;
		this.key = IdentifiersGenerator.generate_new_key(3);
	}
	
	public ClinicCase(){
		this(new AVLTree<Medication>(), new SplayTree<MedicTest>());
	}
	
	public int key() {
		return key;
	}
	
	public AVLTree<Medication> medication(){
		return this.medication;
	}
	
	public SplayTree<MedicTest> tests(){
		return this.tests;
	}
	
	public int cost(){
		return Finder.find_cost(medication);
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
