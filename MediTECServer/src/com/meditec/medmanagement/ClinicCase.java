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
	
	/**
	 * @return el identificador del caso.
	 */
	public int key() {
		return key;
	}
	
	/**
	 * @return El árbol con la medicación.
	 */
	public AVLTree<Medication> medication(){
		return this.medication;
	}
	
	/**
	 * @return el árbol con los exámenes.
	 */
	public SplayTree<MedicTest> tests(){
		return this.tests;
	}
	
	/**
	 * @return la lista de medicamentos del caso.
	 */
	public String get_medication_list(){
		return Finder.get_medication_list(medication);
	}
	
	/**
	 * @return la lista de exámenes del caso.
	 */
	public String get_tests_list(){
		return Finder.get_tests_list(tests);
	}
	
	/**
	 * @return el nombre del caso
	 */
	public String name(){
		return name;
	}
	
	/**
	 * @return el precio total del caso.
	 */
	public int price(){
		return price;
	}
	
	/**
	 * Edita los detalles del caso.
	 * @param tests los exámenes médicos.
	 * @param medication el tratamiento.
	 */
	public void edit_case(String tests, String medication){
		edit_tests(tests);
		edit_medication(medication);
	}
	
	/**
	 * Edita los exámenes médicos.
	 * @param new_tests los nuevos exámenes.
	 */
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
	
	/**
	 * Edita los medicamentos.
	 * @param new_medication los nuevos medicamentos.
	 */
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
	
	/**
	 * Asigna el tratamiento.
	 * @param treatment el tratamiento nuevo.
	 */
	private void set_medication(String treatment){
		
		String[] medication = treatment.split(",");
		
		for(int i = 0; i < medication.length; i++){
			Medication m = XMLHandler.find_medication(medication[i].toLowerCase());
			price += m.price();
			this.medication.insert(m);
		}
	}
	
	/**
	 * asigna los exámenes médicos.
	 * @param tests los nuevos exámenes.
	 */
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
