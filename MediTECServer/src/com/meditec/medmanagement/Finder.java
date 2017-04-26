package com.meditec.medmanagement;

import com.meditec.clientmanagement.Patient;
import com.meditec.datastructures.List;
import com.meditec.datastructures.Node;

public class Finder {
	
	private static List<String> registered_meds = new List<>();
	private static List<String> registered_patients = new List<>();
	
	public static void register(String m, String type){
		if (type.equals("patient")) {
			registered_patients.add_last(m);
		}else{
			registered_meds.add_last(m);
		}
	}

	public static boolean find_medic_by_name(String name){
		
		if (registered_meds.is_empty()) {
			return false;
		}else{
			Node<String> current = registered_meds.peek();
	        boolean found = false;
	
	        while (!found){
	            if (!current.getData().equals(name)){
	                current = current.getNext();
	            }else {
	                found = true;
	            }
	        }
	        return found;
		}
	}
	
	public static boolean find_medic_by_code(String code){
		
		boolean found = false;
		
		if (registered_meds.is_empty()) {
			return found;
		}else{
			Node<String> current = registered_meds.peek();
	
	        while (current.getNext() != null){
	            if (current.getData().equals(code)){
	            	found = true;
	            }else {
	                current = current.getNext();
	            }
	        }
		}
        return found;
	}
	
	public static boolean find_patient(String name){
		
		boolean found = false;
		
		if (registered_patients.is_empty()) {
			return found;
		}else {
			Node<Patient> current = registered_patients.peek();
			
		while(current.getNext() != null){
				if (current.getData().name().equals(name)) {
					found = true;
				}else {
					current = current.getNext();
				}
			}
		}
		return found;
	}

}
