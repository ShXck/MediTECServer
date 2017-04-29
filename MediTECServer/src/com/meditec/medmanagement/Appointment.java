package com.meditec.medmanagement;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.meditec.clientmanagement.Patient;
import com.meditec.datastructures.BinaryTree;
import com.meditec.datastructures.List;
import com.meditec.utilities.IdentifiersGenerator;

public class Appointment implements Comparable<Appointment> {
	
	private int appoitment_number;
	private List<String> symptoms;
	private BinaryTree<ClinicCase> related_clinic_cases;
	private BinaryTree<MedicTest> test_required;
	private boolean is_finished;
	private String patient;
	private Calendar calendar;
	
	public Appointment(int year, int month, int day, String patient_name) {
		this.appoitment_number = IdentifiersGenerator.generate_new_key(3);
		this.symptoms = new List<>();
		this.related_clinic_cases = new BinaryTree<>();
		this.test_required = new BinaryTree<MedicTest>();    
		this.is_finished = false;
		this.patient = patient_name;
		calendar = new GregorianCalendar();
		
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);	
	}

	public int number() {
		return appoitment_number;
	}

	public List<String> symptoms() {
		return symptoms;
	}

	public BinaryTree<ClinicCase> related_clinic_cases() {
		return related_clinic_cases;
	}

	public BinaryTree<MedicTest> test_required() {
		return test_required;
	}

	public boolean is_finished() {
		return is_finished;
	}
	
	public String patient(){
		return patient;
	}
	
	public Calendar calendar(){
		return this.calendar;
	}

	@Override
	public int compareTo(Appointment o) {
		
		if (o.number() > appoitment_number) {
			return 1;
		}else {
			return -1;
		}
	}

}
