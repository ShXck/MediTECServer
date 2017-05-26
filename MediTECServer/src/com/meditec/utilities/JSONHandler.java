package com.meditec.utilities;

import java.util.Calendar;
import org.json.JSONObject;

import org.json.JSONArray;
import com.meditec.datastructures.List;
import com.meditec.datastructures.Node;
import com.meditec.datastructures.TreeNode;
import com.meditec.medmanagement.Appointment;
import com.meditec.medmanagement.ClinicCase;
import com.meditec.medmanagement.Finder;
import com.meditec.medmanagement.Medic;
import com.meditec.medmanagement.MedicTest;
import com.meditec.medmanagement.Medication;
import com.meditec.resources.MedicResources;

public class JSONHandler {
	
	/**
	 * 
	 * @param id el identificador.
	 * @return json con el id.
	 */
	public static String get_identifier(String id){
		JSONObject identifier = new JSONObject();
		identifier.put("id", id);		
		return identifier.toString();
	}
	
	/**
	 * 
	 * @param appointment la cita.
	 * @return json con la informacion básica de la cita.
	 */
	public static String get_json_appointment(Appointment appointment){
		JSONObject appointment_json = new JSONObject();
		appointment_json.put("patient", appointment.patient());
		appointment_json.put("day", appointment.calendar().get(Calendar.DAY_OF_MONTH));
		appointment_json.put("month", appointment.calendar().get(Calendar.MONTH) + 1);
		appointment_json.put("year", appointment.calendar().get(Calendar.YEAR));
		appointment_json.put("cases", appointment.get_cases_list());
		appointment_json.put("symptoms", appointment.symptoms());
		return appointment_json.toString(); 
	}
	
	/**
	 * 
	 * @param appointment la cita.
	 * @return json con información completa de la cita.
	 */
	public static String get_appointment_overview(Appointment appointment){
		
		JSONObject json_appointment = new JSONObject();
		
		String message = "NO HAY CITA ASIGNADA";
		String message2 = "NO DISPONIBLE";
		
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
			json_appointment.put("date", String.valueOf(appointment.calendar().get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(appointment.calendar().get(Calendar.MONTH) + 1) +"/" + String.valueOf(appointment.calendar().get(Calendar.YEAR)));
			json_appointment.put("symptoms", appointment.symptoms());
			try {
				json_appointment.put("medication", appointment.get_appointment_medication());
				json_appointment.put("tests", appointment.get_appointment_tests());
				json_appointment.put("cases", appointment.get_cases_list());
				json_appointment.put("price", appointment.total_cost());
				json_appointment.put("medic", appointment.medic());
				json_appointment.put("finished", appointment.is_finished());
			} catch (NullPointerException e) {
				json_appointment.put("medication", message2);
				json_appointment.put("tests", message2);
				json_appointment.put("cases", message2);
				json_appointment.put("price", message2);
				json_appointment.put("medic", message2);
				json_appointment.put("finished", appointment.is_finished());
			}
		}	
		return json_appointment.toString();
	}
	
	/**
	 * @param json_code json con un id.
	 * @return el id.
	 */
	public static String get_code(String json_code){
		JSONObject code_json = new JSONObject(json_code);
		return code_json.getString("code");
	}
	
	/**
	 * 
	 * @param new_case la información del nuevo caso
	 * @return el caso construido.
	 */
	public static ClinicCase parse_new_clinic_case(String new_case){
		JSONObject new_json_case = new JSONObject(new_case);
		new_json_case.put("id", String.valueOf(IdentifiersGenerator.generate_new_key(4)));
		ClinicCase new_case_obj = new ClinicCase(new_json_case.getString("name"), 
				new_json_case.getString("id"), 
				new_json_case.getString("medication"),
				new_json_case.getString("tests"));
		return new_case_obj;
	}
	
	/**
	 * 
	 * @param medication la medicación del caso.
	 * @param tests los exámenes del caso.
	 * @return el json con la información dle caso.
	 */
	public static String build_json_case_details(String medication, String tests) {
		JSONObject json_details = new JSONObject();
		json_details.put("medication", medication);
		json_details.put("tests", tests);
		return json_details.toString();
	}
	
	/**
	 * 
	 * @param clinic_case un caso clinico.
	 * @return el json con la informacion del caso.
	 */
	public static String build_json_clinic_case(ClinicCase clinic_case){
		JSONObject clinic_case_details = new JSONObject();
		clinic_case_details.put("medication", clinic_case.get_medication_list());
		clinic_case_details.put("tests", clinic_case.get_tests_list());
		clinic_case_details.put("cost", clinic_case.price());
		
		TreeNode<ClinicCase> c = Finder.find_case_node(clinic_case.name(), MedicResources.cases);
		
		clinic_case_details.put("height", c.height());
		clinic_case_details.put("path", c.path());
		clinic_case_details.put("depth", c.depth());
		
		return  clinic_case_details.toString();
	}
	
	/**
	 * 
	 * @param test un exámen médico.
	 * @return un nuevo exámen médico.
	 */
	public static MedicTest parse_new_test(String test){
		JSONObject new_case = new JSONObject(test);
		return new MedicTest(new_case.getString("name"), new_case.getString("cost"), String.valueOf(IdentifiersGenerator.generate_new_key(4)));
	}
	
	/**
	 * 
	 * @param test un exámen médico.
	 * @return el json con la información del exámen.
	 */
	public static String build_test_details(MedicTest test){
		JSONObject json_test = new  JSONObject();
		json_test.put("name", test.name());
		json_test.put("cost", test.cost());
		return json_test.toString();
	} 
	
	/**
	 * 
	 * @param medication un medicamento.
	 * @return el json con la información del medicamento.
	 */
	public static String build_medication_details(Medication medication){
		JSONObject json_test = new  JSONObject();
		json_test.put("name", medication.name());
		json_test.put("cost", medication.price());
		return json_test.toString();
	} 
	
	/**
	 * @param medication la información del nuevo medicamento.
	 * @return un objeto con un nuevo medicamento.
	 */
	public static Medication parse_new_medication(String medication){
		JSONObject new_medication = new JSONObject(medication);
		return new Medication(new_medication.getString("name"), new_medication.getString("cost"), String.valueOf(IdentifiersGenerator.generate_new_key(4)));
	}
	
	/**
	 * método generico para parsear cualquier informacion en json.
	 * @param info la info en json.
	 * @return la información como objeto json.
	 */
	public static JSONObject parse(String info){
		return new JSONObject(info);
	}
	
	/**
	 * @param appointment una cita.
	 * @return un objeto json con la información de la cita.
	 */
	public static JSONObject get_appointment_email_details(Appointment appointment){
		
		JSONObject details = new JSONObject();
		
		details.put("code", appointment.number());
		details.put("date", String.valueOf(appointment.calendar().get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(appointment.calendar().get(Calendar.MONTH) + 1) +"/" + String.valueOf(appointment.calendar().get(Calendar.YEAR)));
		details.put("symptoms", appointment.symptoms());
		details.put("medication", appointment.get_appointment_medication());
		details.put("tests", appointment.get_appointment_tests());
		details.put("cases", appointment.get_cases_list());
		details.put("price", appointment.total_cost());
		return details;
	}
	
	/**
	 * Construye un json de los comentarios de un médico.
	 * @param comments la lista de comentarios.
	 * @return la lista en json.
	 */
	public static String get_json_comments(List<String> comments){
		
		JSONObject json_comments = new JSONObject();
		JSONArray array = new JSONArray();
		
		if (!comments.is_empty()) {
			
			Node<String> current = comments.peek();
			int count = 1;
			
			while(current != null){
				array.put(current.data());
				current = current.next();
			}
			json_comments.put("comments", array);
			return json_comments.toString();
		}else {
			json_comments.put("comments", array);
			return json_comments.toString();
		}
	}
	
	/**
	 * Procesa los síntomas captados por voz.
	 * @param info los síntomas.
	 * @param list la lista donde se agregan los síntomas.
	 */
	public static void process_recorded_symptoms(String info, List<String> list) {
		
		JSONArray array = new JSONArray(info);
		
		for(int i = 0; i < array.length(); i++){
			System.out.println(array.get(i));
			list.add_last((String) array.get(i));
		}
	}
	
}
