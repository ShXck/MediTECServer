package com.meditec.utilities;

import java.util.Calendar;
import org.json.JSONObject;

import com.ibm.json.java.JSONArray;
import com.meditec.datastructures.List;
import com.meditec.datastructures.Node;
import com.meditec.medmanagement.Appointment;
import com.meditec.medmanagement.ClinicCase;
import com.meditec.medmanagement.Medic;
import com.meditec.medmanagement.MedicTest;
import com.meditec.medmanagement.Medication;

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
		return appointment_json.toString();    //TODO: Agregar al json informacion como los sintomas registrados en la deteccion por voz y demas, usando un algoritmo de nevgacion en el arbol para recoger todos los sintomas registrados.
	}
	
	public static String get_appointment_overview(Appointment appointment){
		
		JSONObject json_appointment = new JSONObject();
		
		String message = "NO HAY CITA ASIGNADA";
		
		if (appointment == null) {
			json_appointment.put("code", message);
			json_appointment.put("date",message);
			json_appointment.put("symptoms", message);
			json_appointment.put("medication", message);
			json_appointment.put("tests", message);
			json_appointment.put("cases", message);
			json_appointment.put("price", message);
			json_appointment.put("medic", message);
			json_appointment.put("finished", false);
		}else{
			json_appointment.put("code", appointment.number());
			json_appointment.put("date", String.valueOf(appointment.calendar().get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(appointment.calendar().get(Calendar.MONTH)) +"/" + String.valueOf(appointment.calendar().get(Calendar.YEAR)));
			json_appointment.put("symptoms", appointment.symptoms());
			//TODO: modificar solo se estan mandando los medicamentos y examenes de un solo caso.
			json_appointment.put("medication", appointment.related_clinic_cases().root().data().get_medication_list());
			json_appointment.put("tests", appointment.related_clinic_cases().root().data().get_tests_list());
			json_appointment.put("cases", appointment.get_cases_list());
			json_appointment.put("price", appointment.total_cost());
			json_appointment.put("medic", appointment.medic());
			json_appointment.put("finished", appointment.is_finished());
		}	
		return json_appointment.toString();
	}
	
	public static String get_code(String json_code){
		JSONObject code_json = new JSONObject(json_code);
		return code_json.getString("code");
	}
	
	public static ClinicCase parse_new_clinic_case(String new_case){
		JSONObject new_json_case = new JSONObject(new_case);
		new_json_case.put("id", String.valueOf(IdentifiersGenerator.generate_new_key(4)));
		ClinicCase new_case_obj = new ClinicCase(new_json_case.getString("name"), 
				new_json_case.getString("id"), 
				new_json_case.getString("medication"),
				new_json_case.getString("tests"));
		return new_case_obj;
	}
	
	public static String build_json_case_details(String medication, String tests) {
		JSONObject json_details = new JSONObject();
		json_details.put("medication", medication);
		json_details.put("tests", tests);
		return json_details.toString();
	}
	
	public static String parse_clinic_case(ClinicCase clinic_case){
		JSONObject clinic_case_details = new JSONObject();
		clinic_case_details.put("medication", clinic_case.get_medication_list());
		clinic_case_details.put("tests", clinic_case.get_tests_list());
		clinic_case_details.put("cost", clinic_case.price());
		return  clinic_case_details.toString();
	}
	
	public static MedicTest build_new_test(String test){
		JSONObject new_case = new JSONObject(test);
		return new MedicTest(new_case.getString("name"), new_case.getString("cost"), String.valueOf(IdentifiersGenerator.generate_new_key(4)));
	}
	
	public static String build_test_details(MedicTest test){
		JSONObject json_test = new  JSONObject();
		json_test.put("name", test.name());
		json_test.put("cost", test.cost());
		return json_test.toString();
	} 
	
	public static String build_medication_details(Medication medication){
		JSONObject json_test = new  JSONObject();
		json_test.put("name", medication.name());
		json_test.put("cost", medication.price());
		return json_test.toString();
	} 
	
	public static Medication build_new_medication(String medication){
		JSONObject new_medication = new JSONObject(medication);
		return new Medication(new_medication.getString("name"), new_medication.getString("cost"), String.valueOf(IdentifiersGenerator.generate_new_key(4)));
	}
	
	public static JSONObject parse(String info){
		return new JSONObject(info);
	}
	
	public static JSONObject appointment_to_json(Appointment appointment){
		
		JSONObject details = new JSONObject();
		
		details.put("code", appointment.number());
		details.put("date", String.valueOf(appointment.calendar().get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(appointment.calendar().get(Calendar.MONTH)) +"/" + String.valueOf(appointment.calendar().get(Calendar.YEAR)));
		details.put("symptoms", appointment.symptoms());
		details.put("medication", appointment.related_clinic_cases().root().data().get_medication_list());
		details.put("tests", appointment.related_clinic_cases().root().data().get_tests_list());
		details.put("cases", appointment.get_cases_list());
		details.put("price", appointment.total_cost());
		
		return details;
	}
	
	public static String get_json_comments(List<String> comments){
		
		JSONObject json_comments = new JSONObject();
		
		if (!comments.is_empty()) {
			
			Node<String> current = comments.peek();
			int count = 1;
			
			while(current.getNext() != null){
				json_comments.put(String.valueOf(count), current.getData());
				count++;
				current = current.getNext();
			}
			json_comments.put(String.valueOf(count), current.getData());
			json_comments.put("count", count);
			return json_comments.toString();
		}else {
			return json_comments.toString();
		}
	}
}
