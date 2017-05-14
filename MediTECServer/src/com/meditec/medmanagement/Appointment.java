package com.meditec.medmanagement;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONObject;

import com.meditec.datastructures.BinaryTree;
import com.meditec.datastructures.List;
import com.meditec.utilities.IdentifiersGenerator;
import com.meditec.utilities.JSONHandler;

public class Appointment implements Comparable<Appointment> {
	
	private int appoitment_number;
	private List<String> symptoms;
	private BinaryTree<ClinicCase> related_clinic_cases;
	private boolean is_finished;
	private String patient;
	private String medic;
	private Calendar calendar;
	
	public Appointment(int year, int month, int day, String patient_name, String medic) {
		this.appoitment_number = IdentifiersGenerator.generate_new_key(3);
		this.symptoms = new List<>();
		this.related_clinic_cases = new BinaryTree<>(); 
		this.is_finished = false;
		this.patient = patient_name;
		this.medic = medic;
		calendar = new GregorianCalendar();
		
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);	
	}

	public int number() {
		return appoitment_number;
	}

	public List<String> symptoms_list() {
		return symptoms;
	}
	
	public String symptoms(){
		
		String result = "";
		
		for(int i = 0; i < symptoms.get_size(); i++){
			result += symptoms.get(i).getData() +  ",";
		}
		return result;
	}
	
	public void save_recorded_symptoms(String symptoms){
		JSONHandler.process_recorded_symptoms(symptoms, symptoms_list());
	}
	
	public String get_cases_list(){
		return Finder.get_cases_list(related_clinic_cases);
	}
	

	public BinaryTree<ClinicCase> related_clinic_cases() {
		return related_clinic_cases;
	}

	public void end(){
		is_finished = true;
	}
	
	public boolean is_finished(){
		return is_finished;
	}
	
	public String patient(){
		return patient;
	}
	
	public Calendar calendar(){
		return this.calendar;
	}
	
	public int total_cost(){
		return Finder.find_appointment_cost(related_clinic_cases);
	}
	
	public String medic() {
		return this.medic;
	}

	
	@Override
	public int compareTo(Appointment o) {
		
		if (o.number() > appoitment_number) {
			return 1;
		}else if (o.number() < appoitment_number) {
			return -1;
		}else{
			return 0;
		}
	}

}
