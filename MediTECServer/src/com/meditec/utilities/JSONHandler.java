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
	
	public static String get_updated_symptoms(String json_updated_appointment){
		
		JSONObject json_appointment = new JSONObject(json_updated_appointment);
		
		return json_appointment.getString("symptoms");
	}
	
	public static String get_updated_medication(String json_updated_appointment){
		
		JSONObject json_appointment = new JSONObject(json_updated_appointment);
		
		return json_appointment.getString("medication");
	}
	
	public static String get_updated_tests(String json_updated_appointment){
		
		JSONObject json_appointment = new JSONObject(json_updated_appointment);
		
		return json_appointment.getString("tests");
	}
	
	public static String get_updated_cases(String json_updated_appointment){
		
		JSONObject json_appointment = new JSONObject(json_updated_appointment);
		
		return json_appointment.getString("clinic_cases");
	}
	
	public static String get_appointment_overview(Appointment appointment){
		
		JSONObject json_appointment = new JSONObject();
		
		String message = "NO HAY CITA ASIGNADA";
		
		if (appointment == null) {
			json_appointment.put("code", message);
			json_appointment.put("date",message);
			json_appointment.put("symptoms", message);
		}else{
			json_appointment.put("code", appointment.number());
			json_appointment.put("date", String.valueOf(appointment.calendar().get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(appointment.calendar().get(Calendar.MONTH)) +"/" + String.valueOf(appointment.calendar().get(Calendar.YEAR)));
			json_appointment.put("symptoms", appointment.symptoms());
		}	
		return json_appointment.toString();
	}
	
	public static String get_code(String json_code){
		JSONObject code_json = new JSONObject(json_code);
		return code_json.getString("code");
	}
}
