package com.meditec.medmanagement;

import com.meditec.datastructures.AVLTree;
import com.meditec.datastructures.List;
import com.meditec.utilities.XMLHandler;
import com.sun.xml.internal.bind.v2.runtime.Name;

public class Agenda {
	
	private AVLTree<Appointment> agenda;
	
	public Agenda(){
		agenda = new AVLTree<>();
	}
	
	public void remove_appointment(String client_name){
		agenda.remove(Finder.get_appointment(client_name, agenda));
	}
	
	public void schedule_appointment(Appointment appointment){
		agenda.insert(appointment);
	}
	
	public Appointment get_appointment_info(String patient_name){
		return Finder.get_appointment(patient_name, agenda);
	}
	
	public AVLTree<Appointment> appointments(){
		return this.agenda;
	}
	
	public void edit_appointment(String symptoms, String medication, String tests, String clinic_cases, Appointment appointment){
		ClinicCase new_case = new ClinicCase();
		appointment.related_clinic_cases().insert(new_case.key(), new_case);
		edit_symptoms(symptoms, appointment);
		edit_medication(medication, appointment, new_case);
		edit_tests(tests, appointment, new_case);
		edit_cases(clinic_cases, appointment);
	}
	
	private void edit_symptoms(String symptoms, Appointment appointment){
		String[] result = symptoms.split(",");
		
		for(int i = 0; i < result.length; i++){
			appointment.symptoms_list().add_last(result[i]);
		}
	}
	
	private void edit_medication(String medication, Appointment appointment, ClinicCase clinic_case){
		
		String[] result = medication.split(",");
		
		for(int i = 0; i < result.length; i++){
			try{
				Medication m = XMLHandler.find_medication(result[i].toLowerCase());
				clinic_case.medication().insert(m);
			}catch (NullPointerException npe) {
				System.out.println(result[i].toLowerCase());
			}
		}
	}
	
	private void edit_tests(String tests, Appointment appointment, ClinicCase clinic_case){
		String[] result = tests.split(",");
	}
	
	private void edit_cases(String cases, Appointment appointment){
		
	}
	
}
