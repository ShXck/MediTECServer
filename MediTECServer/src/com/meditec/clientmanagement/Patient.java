package com.meditec.clientmanagement;
import com.meditec.medmanagement.Appointment;
import com.meditec.medmanagement.ClinicCase;
import com.meditec.utilities.IdentifiersGenerator;
import com.sun.prism.paint.Paint;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Patient implements Comparable<Patient>{
	
	private String name;
	private String email;
	private int patient_code;
	//private ClinicCase clinic_case;
	private Appointment current_appointment;
	private Appointment last_appointment;
	
	public Patient(String name, String email){
		this.name = name;
		this.email = email;
		//this.clinic_case = null;
		this.patient_code = IdentifiersGenerator.generate_new_key(4);
		this.current_appointment = null;
		this.last_appointment = null;
	}
	
	/**
	 * @return el nombre del paciente.
	 */
	public String name(){
		return this.name;
	}
	
	/**
	 * @return id del paciente.
	 */
	public int code(){
		return this.patient_code;
	}
	
	/**
	 * @return el email del paciente.
	 */
	public String email(){
		return this.email;
	}
	
	/**
	 * @return la cita actual.
	 */
	public Appointment current_appointment(){
		return current_appointment;
	}
	
	/**
	 * Establece la cita actual.
	 * @param appointment la nueva cita.
	 */
	public void set_current_appointment(Appointment appointment) {
		this.current_appointment = appointment;
	}
	
	/** 
	 * @return la ultima cita.
	 */
	public Appointment last_appointment(){
		return last_appointment;
	}
	
	/**
	 * Establece la ultima cita.
	 * @param appointment la ultima cita.
	 */
	public void set_last_appointment(Appointment appointment) {
		this.last_appointment = appointment;
	}
	
	@Override
	public int compareTo(Patient o) {
		if (o.code() > patient_code) {
			return 1;
		}else if (o.code() < patient_code) {
			return -1;
		}else{
			return 0;
		}
	}
}
