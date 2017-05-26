package com.meditec.medmanagement;

import com.meditec.datastructures.AVLTree;
import com.meditec.resources.MedicResources;
import com.meditec.utilities.XMLHandler;

public class Agenda {
	
	private AVLTree<Appointment> agenda;
	
	public Agenda(){
		agenda = new AVLTree<>();
	}
	
	/**
	 * Elimina una cita del arbol.
	 * @param client_name el nombre del cliente.
	 */
	public void remove_appointment(String client_name){
		agenda.remove(Finder.get_appointment(client_name, agenda));
	}
	
	/**
	 * Asigna una cita.
	 * @param appointment la nueva cita.
	 */
	public void schedule_appointment(Appointment appointment){
		agenda.insert(appointment);
	}
	
	/**
	 * @param patient_name el nombre del paciente.
	 * @return la informacion de la cita.
	 */
	public Appointment get_appointment_info(String patient_name){
		return Finder.get_appointment(patient_name, agenda);
	}
	
	/**
	 * @return la agenda.
	 */
	public AVLTree<Appointment> appointments(){
		return this.agenda;
	}
	
	/**
	 * Edita los detalles de una cita.
	 * @param symptoms los sintomas.
	 * @param medication el tratamiento.
	 * @param tests los examenes medicos.
	 * @param clinic_cases los casos clinicos.
	 * @param appointment la cita en cuestion.
	 */
	public void edit_appointment(String medication, String tests, String clinic_cases, Appointment appointment){
		edit_cases(clinic_cases, appointment);
		edit_tests(tests, appointment);	
		edit_medication(medication, appointment);
	}
	
	/*
	 * Edita el tratamiento.
	 */
	private void edit_medication(String medication, Appointment appointment){
		String[] result = medication.split(",");
		if(!result[0].equals("none")){
			for(int k = 0; k < result.length; k++){
				if (!Finder.contains_medication(result[k].toLowerCase(), appointment.related_clinic_cases())) {
					appointment.related_clinic_cases().root().data().medication().insert(XMLHandler.find_medication(result[k]));
				}
			}
		}
	}
	
	/**
	 * Edita los examenes.
	 * @param tests los nuevos examenes.
	 * @param appointment la cita.
	 */
	private void edit_tests(String tests, Appointment appointment){
	
		String[] result = tests.split(",");
		if (!result[0].equals("none")) {
			for(int j = 0; j < result.length; j++){
				try{
					MedicTest medic_test_to_find = Finder.find_test(result[j], appointment.related_clinic_cases().root().data().tests());
				}catch (NullPointerException e) {
					MedicTest medic_test = XMLHandler.find_test(result[j].toLowerCase());
					appointment.related_clinic_cases().root().data().tests().insert(medic_test, medic_test.id());
				}
			}
		}	
	}
	
	/**
	 * Edita los casos clínicos.
	 * @param cases los casos.
	 * @param appointment la cita.
	 */
	private void edit_cases(String cases, Appointment appointment){
		String[] result = cases.split(",");
		if (!result[0].equals("none")) {
			for(int i = 0; i < result.length; i++){
				ClinicCase clinicCase = XMLHandler.find_case(result[i].toLowerCase());
				appointment.related_clinic_cases().insert(clinicCase.key(), clinicCase);
			}
		}
	}
}
