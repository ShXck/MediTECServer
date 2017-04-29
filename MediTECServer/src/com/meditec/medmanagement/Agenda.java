package com.meditec.medmanagement;

import com.meditec.datastructures.AVLTree;
import com.meditec.datastructures.List;
import com.sun.xml.internal.bind.v2.runtime.Name;

public class Agenda {
	
	private AVLTree<Appointment> agenda;
	
	public Agenda(){
		agenda = new AVLTree<>();
	}
	
	public void remove_appointment(String client_name){
		
	}
	
	public void schedule_appointment(Appointment appointment){
		agenda.insert(appointment);
	}
	
	public void edit_appointment_info(Appointment outdated_appointment, Appointment updated_appointment){
		
	}
	
	public Appointment get_appointment_info(String patient_name){
		return Finder.get_appointment(patient_name, agenda);
	}
	
	public AVLTree<Appointment> appointments(){
		return this.agenda;
	}

}
