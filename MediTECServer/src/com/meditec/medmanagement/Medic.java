package com.meditec.medmanagement;

import com.meditec.utilities.IdentifiersGenerator;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Medic implements Comparable<Medic>{
	
	private String code;
	
	private Agenda agenda;
	
	private String name;
	
	private String email;
	
	public Medic(String name, String email){
		this.code = IdentifiersGenerator.generate_new_code(4);
		this.agenda = new Agenda();
		this.name = name;
		this.email = email;
	}

	public String code() {
		return code;
	}

	public Agenda agenda() {
		return agenda;
	}

	public String name() {
		return name;
	}

	public String email() {
		return email;
	}

	@Override
	public int compareTo(Medic o) {
		if (o.code.equals(code)) {
			return 0;
		}else {
			return -1;
		}
	}
	
	

}
