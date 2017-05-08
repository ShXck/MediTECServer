package com.meditec.medmanagement;

import com.meditec.datastructures.AVLTree;
import com.meditec.resources.MedicResources;
import com.meditec.utilities.XMLHandler;

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
		edit_symptoms(symptoms, appointment);
		edit_cases(clinic_cases, appointment);
		edit_tests(tests, appointment);	
		edit_medication(medication, appointment);
	}
	
	//TODO: EN LUGAR DE MANDARLOS EN STRING MANDARLOS POR JSON Array.
	private void edit_medication(String medication, Appointment appointment){
		
		String[] result = medication.split(",");
		
		for(int k = 0; k < result.length; k++){
			try{
				Medication medication3 = Finder.find_medication(result[k].toLowerCase(), appointment.related_clinic_cases().root().data().medication());
			}catch (NullPointerException e) {
				appointment.related_clinic_cases().root().data().medication().insert(XMLHandler.find_medication(result[k].toLowerCase()));
			}
		}
	}
	
	private void edit_symptoms(String symptoms, Appointment appointment){
		String[] result = symptoms.split(",");
		appointment.symptoms_list().clear_list();
		for(int i = 0; i < result.length; i++){
			appointment.symptoms_list().add_last(result[i]);
		}
	}
	
	private void edit_tests(String tests, Appointment appointment){
		String[] result = tests.split(",");
		
		for(int j = 0; j < result.length; j++){
			try{
				MedicTest medic_test_to_find = Finder.find_test(result[j], appointment.related_clinic_cases().root().data().tests());
			}catch (NullPointerException e) {
				MedicTest medic_test = XMLHandler.find_test(result[j].toLowerCase());
				appointment.related_clinic_cases().root().data().tests().insert(medic_test, medic_test.id());
			}
		}
	}
	
	private void edit_cases(String cases, Appointment appointment){
		String[] result = cases.split(",");
		
		for(int i = 0; i < result.length; i++){
			System.out.println(result[i]);
			ClinicCase clinicCase = Finder.find_case(result[i], MedicResources.cases);
			appointment.related_clinic_cases().insert(clinicCase.key(), clinicCase);
		}

	}
	
}
