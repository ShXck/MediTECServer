package com.meditec.medmanagement;

import com.meditec.datastructures.AVLTree;
import com.meditec.datastructures.SplayTree;
import com.meditec.utilities.XMLHandler;

public class ClinicCase implements Comparable<ClinicCase>{
	
	private AVLTree<Medication> medication;
	private SplayTree<MedicTest> tests;
	private String name;
	private int price;
	private int key;
	
	public ClinicCase(String name, String id, String treatment, String tests_case, AVLTree<Medication> medication, SplayTree<MedicTest> tests){
		this.medication = medication;
		this.tests = tests;
		this.key = Integer.parseInt(id);
		this.name = name;
		set_medication(treatment);
		set_tests(tests_case);	
	}
	
	public ClinicCase(String name, String id, String treatment, String tests){
		this(name, id , treatment, tests, new AVLTree<Medication>(), new SplayTree<MedicTest>());
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
	
	public String get_medication_list(){
		return Finder.get_medication_list(medication);
	}
	
	public String get_tests_list(){
		return Finder.get_tests_list(tests);
	}
	
	public String name(){
		return name;
	}
	
	public int price(){
		return price;
	}
	
	public void edit_case(String tests, String medication){
		edit_tests(tests);
		edit_medication(medication);
	}
	
	private void edit_tests(String new_tests){
		String[] tests = new_tests.split(",");
		
		for(int k = 0; k < tests.length; k++){
			try{
				MedicTest t = Finder.find_test(tests[k], this.tests);
			}catch (NullPointerException e) {
				MedicTest m = XMLHandler.find_test(tests[k].toLowerCase());
				this.tests.insert(m, m.id());
				price += m.cost();
			}
		}
	}
	
	private void edit_medication(String new_medication){
		String[] medication = new_medication.split(",");
		
		for(int k = 0; k < medication.length; k++){
			try{
				Medication m = Finder.find_medication(medication[k], this.medication);
			}catch (NullPointerException e) {
				Medication t = XMLHandler.find_medication(medication[k].toLowerCase());
				this.medication.insert(t);
				price += t.price();
			}
		}
	}
	
	private void set_medication(String treatment){
		
		String[] medication = treatment.split(",");
		
		for(int i = 0; i < medication.length; i++){
			Medication m = XMLHandler.find_medication(medication[i].toLowerCase());
			price += m.price();
			this.medication.insert(m);
		}
	}
	
	private void set_tests(String tests){
		String[] tests_list = tests.split(",");
		
		for(int i = 0; i < tests_list.length; i++){
			MedicTest m = XMLHandler.find_test(tests_list[i].toLowerCase());
			price += m.cost();
			this.tests.insert(m, m.id());
		}
	}

	@Override
	public int compareTo(ClinicCase o) {
		if (o.key() > key) {
			return 1;
		}else if (o.key < key) {
			return -1;
		}else{
			return 0;
		}
	}

}
