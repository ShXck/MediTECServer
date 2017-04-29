package com.meditec.utilities;

import java.util.Calendar;
import org.json.JSONObject;

import com.meditec.medmanagement.Appointment;

public class JSONHandler {
	
	public static String get_identifier(String id){
		JSONObject identifier = new JSONObject();
		identifier.put("id", id);		
		return identifier.toString();
	}
	
	public static String get_json_appointment(Appointment appointment){
		JSONObject appointment_json = new JSONObject();
		appointment_json.put("patient", appointment.patient());
		appointment_json.put("day", appointment.calendar().get(Calendar.DAY_OF_MONTH));
		appointment_json.put("month", appointment.calendar().get(Calendar.MONTH));
		appointment_json.put("year", appointment.calendar().get(Calendar.YEAR));
		return appointment_json.toString();    //TODO: Agregar al json informacion como los sintomas y demas, usando un algoritmo de nevgacion en el arbol para recoger todos los sintomas registrados.
	}
	
	public static parse_updated_appointment(){
		
	}

}
