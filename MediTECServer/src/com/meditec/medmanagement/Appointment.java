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
	
	/**
	 * @return el numero de cita.
	 */
	public int number() {
		return appoitment_number;
	}
	
	/**
	 * @return la lista de síntomas.
	 */
	public List<String> symptoms_list() {
		return symptoms;
	}
	
	/**
	 * @return la lista de síntomas como string.
	 */
	public String symptoms(){
		
		String result = "";
		
		for(int i = 0; i < symptoms.size(); i++){
			result += symptoms.get(i).data() +  ",";
		}
		return result;
	}
	
	public String get_appointment_medication() {
		return Finder.get_appointment_medication(related_clinic_cases);
	}
	
	public String get_appointment_tests(){
		return Finder.get_appointment_tests(related_clinic_cases);
	}
	
	/**
	 * Guarda los síntomas registrados por voz.
	 * @param symptoms los síntomas.
	 */
	public void save_recorded_symptoms(String symptoms){
		JSONHandler.process_recorded_symptoms(symptoms, symptoms_list());
	}
	
	/**
	 * @return los casos clinicos de la cita.
	 */
	public String get_cases_list(){
		return Finder.get_cases_list(related_clinic_cases);
	}
	
	
	/**
	 * @return el árbol de casos clínicos.
	 */
	public BinaryTree<ClinicCase> related_clinic_cases() {
		return related_clinic_cases;
	}
	
	/**
	 * Termina la cita.
	 */
	public void end(){
		is_finished = true;
	}
	
	/**
	 * @return si la cita ha terminado o no.
	 */
	public boolean is_finished(){
		return is_finished;
	}
	
	/**
	 * @return El nombre del paciente.
	 */
	public String patient(){
		return patient;
	}
	
	/**
	 * @return la fecha de la cita.
	 */
	public Calendar calendar(){
		return this.calendar;
	}
	
	/**
	 * @return el costo de la cita.
	 */
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
