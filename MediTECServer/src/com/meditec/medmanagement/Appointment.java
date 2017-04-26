package com.meditec.medmanagement;

import com.meditec.datastructures.List;

public class Appointment implements Comparable<Appointment> {
	
	private int appoitment_number;
	private List<String> symptoms;
	private List<ClinicCase> related_clinic_cases;
	private List<MedicTest> test_required;
	private boolean is_finished;
	
	public Appointment(int number) {
		this.appoitment_number = number;
		this.symptoms = new List<>();
		this.related_clinic_cases = new List<>();
		this.test_required = new List<>();
		this.is_finished = false;
	}

	public int number() {
		return appoitment_number;
	}

	public List<String> symptoms() {
		return symptoms;
	}

	public List<ClinicCase> related_clinic_cases() {
		return related_clinic_cases;
	}

	public List<MedicTest> test_required() {
		return test_required;
	}

	public boolean is_finished() {
		return is_finished;
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
