package com.meditec.clientmanagement;
import com.meditec.medmanagement.ClinicCase;
import com.meditec.utilities.IdentifiersGenerator;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("patient")
public class Patient implements Comparable<Patient>{
	
	@XStreamAlias("name")
	private String name;
	
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("code")
	private int patient_code;
	
	private ClinicCase clinic_case;
	
	
	public Patient(String name, String email){
		this.name = name;
		this.email = email;
		this.clinic_case = null;
		this.patient_code = IdentifiersGenerator.generate_new_patient_key(4);
		
		System.out.println(name);
		System.out.println(email);
	}
	
	public String name(){
		return this.name;
	}
	
	public ClinicCase clinic_case(){
		return this.clinic_case;
	}
	
	public void set_clinic_case(ClinicCase new_case){
		this.clinic_case = new_case;
	}
	
	public int code(){
		return this.patient_code;
	}
	
	public String email(){
		return this.email;
	}

	@Override
	public int compareTo(Patient o) {
		return -Integer.compare(o.code(), patient_code);
	}
}
