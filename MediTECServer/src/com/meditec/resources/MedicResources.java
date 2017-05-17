package com.meditec.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import com.meditec.clientmanagement.Mailer;
import com.meditec.clientmanagement.Patient;
import com.meditec.datastructures.AVLTree;
import com.meditec.datastructures.BinaryTree;
import com.meditec.datastructures.SplayTree;
import com.meditec.medmanagement.Medic;
import com.meditec.medmanagement.MedicTest;
import com.meditec.medmanagement.Medication;
import com.meditec.utilities.IdentifiersGenerator;
import com.meditec.utilities.JSONHandler;
import com.meditec.utilities.XMLHandler;
import com.meditec.medmanagement.Appointment;
import com.meditec.medmanagement.ClinicCase;
import com.meditec.medmanagement.Finder;

@Path("/medics")
public class MedicResources {
	
	public static SplayTree<Medic> medic_tree = new SplayTree<>();
	public static BinaryTree<ClinicCase> cases = new BinaryTree<>();
	public static SplayTree<MedicTest> tests = new SplayTree<>();
	public static AVLTree<Medication> medication = new AVLTree<>();
	private static boolean flag = false;
	
	/**
	 * Revisa el estado de la cuenta de los médicos. Si no se encuentra registrado se agrega.
	 * @param json_info informacion básica del médico.
	 * @return el identificador del médico.
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response log_medic(String json_info){
		
		JSONObject medic_json = new JSONObject(json_info);
		
		try{
			Medic m = Finder.find_medic_by_name(medic_json.getString("name"));
		}catch (NullPointerException e) {
			Medic new_medic = new Medic(medic_json.getString("name"), medic_json.getString("email"));
			process_medic(new_medic);
			if (!flag) {
				XMLHandler.add_cases_to_tree(cases);
				XMLHandler.add_tests_to_tree(tests);
				XMLHandler.add_medication_to_tree(medication);
				create_dummy_medics();
				flag = true;
			}
			return Response.ok(JSONHandler.get_identifier(new_medic.code())).build();
		}
		
		Medic medic = Finder.find_medic_by_name(medic_json.getString("name"));
		
		return Response.ok(JSONHandler.get_identifier(medic.code())).build();
	}
	
	/**
	 * @param id la identificación del médico.
	 * @return Las citas de la agenda.
	 */
	@GET
	@Path("/{id}/appointments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_medic_appointments(@PathParam("id") String id){
		JSONObject appointments = Finder.get_medic_appointments(id);
		return Response.ok(appointments.toString()).build();
	}
	
	/**
	 * @param id la identificación del médico.
	 * @param patient_name el nombre del paciente.
	 * @return la información de la cita.
	 */
	@GET
	@Path("/{id}/appointments/{patient}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_client_appointment(@PathParam("id") String id, @PathParam("patient") String patient_name){
		Medic medic = Finder.find_medic_by_code(id);
		Appointment appointment = medic.agenda().get_appointment_info(patient_name);
		return Response.ok(JSONHandler.get_json_appointment(appointment)).build();
	}
	
	/**
	 * Edita los detalles de la cita.
	 * @param updated_info la información actualizada.
	 * @param id la identificación del médico.
	 * @param patient el nombre del paciente.
	 * @return un mensaje de verificación.
	 */
	@PUT
	@Path("/{id}/appointments/{patient}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response edit_appointment_info(String updated_info, @PathParam("id") String id, @PathParam("patient") String patient){
		Medic medic = Finder.find_medic_by_code(id);
		Appointment appointment = medic.agenda().get_appointment_info(patient);
		JSONObject info = new JSONObject(updated_info);
		medic.agenda().edit_appointment(info.getString("symptoms"), info.getString("medication"), info.getString("tests"), info.getString("cases"), appointment);
		return Response.ok("Appointment edited!").build();
	}
	
	/**
	 * Termina la cita.
	 * @param code identificador del médico.
	 * @param patient_name nombre del paciente.
	 * @return Mensaje de verificación.
	 */
	@DELETE
	@Path("/{id}/appointments/{patient}")
	public Response end_appointment(@PathParam("id") String code, @PathParam("patient") String patient_name){
		Medic medic = Finder.find_medic_by_code(code);
		Appointment appointment = medic.agenda().get_appointment_info(patient_name);
		Patient patient = Finder.find_patient(patient_name);
		appointment.end();
		JSONObject details = JSONHandler.get_appointment_email_details(appointment);
		Mailer.send_appointment_info(patient.email(), details.getString("symptoms"), details.getString("medication"), details.getString("tests"), details.getString("cases"), details.getInt("price"));
		medic.agenda().remove_appointment(patient_name);
		return Response.ok("appointment finished succesfully").build();
	}
	
	/**
	 * Crea un nuevo caso clinico.
	 * @param case_info la información del nuevo caso.
	 * @return Mensaje de verificación.
	 */
	@POST
	@Path("/cases/new_case")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create_clinic_case(String case_info){
		System.out.println(case_info);
		ClinicCase new_case = JSONHandler.parse_new_clinic_case(case_info);
		cases.insert(new_case.key(), new_case);
		XMLHandler.write_case(new_case.name(), new_case.get_medication_list(), new_case.get_tests_list(), String.valueOf(new_case.key()));
		return Response.ok("Clinic case succesfully created").build();
	}
	
	/**
	 * @return la lista de casos clínicos.
	 */
	@GET
	@Path("/cases")
	public Response get_cases_list() {
		return Response.ok(Finder.get_all_cases(cases).toString()).build();
	}
	
	/**
	 * Elimina un caso clínico.
	 * @param case_name el nombre del caso.
	 * @return Mensaje de verificación.
	 */
	@DELETE
	@Path("/cases/{name}")
	public Response delete_clinic_case(@PathParam("name") String case_name) {
		cases.remove(Finder.find_case(case_name,cases).key());
		return Response.ok("Case removed successfully").build();
	}
	
	/**
	 * @param name nombre del caso clínico.
	 * @return los detalles del caso.
	 */
	@GET
	@Path("/cases/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_case_details(@PathParam("name") String name) {
		return Response.ok(JSONHandler.build_json_clinic_case(Finder.find_case(name,cases))).build();
	}
	
	/**
	 * Edita detalles de un caso clínico.
	 * @param json_details los detalles actualizados.
	 * @param case_name el nombre del caso.
	 * @return Mensaje de verificación.
	 */
	@PUT
	@Path("/cases/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response edit_case_details(String json_details, @PathParam("name") String case_name){
		ClinicCase clinic_case = Finder.find_case(case_name, cases);
		JSONObject details = new JSONObject(json_details);
		clinic_case.edit_case(details.getString("tests"), details.getString("medication"));
		return Response.ok("Clinic Case Edited!").build();
	}
	
	/**
	 * @return la lista de exámenes médicos.
	 */
	@GET
	@Path("/tests")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_all_tests(){
		return Response.ok(Finder.get_all_tests(tests).toString()).build();
	}
	
	/**
	 * Crear un nuevo examen.
	 * @param new_test los detalles del examen.
	 * @return Mensaje de verificación.
	 */
	@POST
	@Path("/tests/new_test")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create_new_test(String new_test){
		MedicTest medic_test = JSONHandler.parse_new_test(new_test);
		tests.insert(medic_test, medic_test.id());
		XMLHandler.write_test(medic_test.name(), String.valueOf(medic_test.cost()), String.valueOf(medic_test.id()));
		return Response.ok("Test created succesfully").build();
	}
	
	/**
	 * Elimina un examen médico.
	 * @param name el nombre del examen.
	 * @return Mensaje de verificación.
	 */
	@DELETE
	@Path("/tests/{name}")
	public Response delete_test(@PathParam("name") String name){
		tests.remove(Finder.find_test(name, tests).id());
		return Response.ok("Test " + name + " eliminated succesfully").build();
		
	}
	
	/**
	 * Edita detalles de un examen médico.
	 * @param new_info información actualizada.
	 * @param name el nombre del examen.
	 * @return
	 */
	@PUT
	@Path("/tests/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response edit_test(String new_info, @PathParam("name") String name){
		MedicTest m = Finder.find_test(name, tests);
		JSONObject json_test = JSONHandler.parse(new_info);
		m.edit_name(json_test.getString("name"));
		m.edit_price(json_test.getInt("cost"));
		return Response.ok("Test updated!").build();
	}
	
	/**
	 * @param name el nombre del examen.
	 * @return los detalles del examen.
	 */
	@GET
	@Path("/tests/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_test_details(@PathParam("name") String name){
		return Response.ok(JSONHandler.build_test_details(Finder.find_test(name, tests))).build();
	}
	
	/**
	 * @return la lista de los medicamentos.
	 */
	@GET
	@Path("/medication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_all_medication(){
		return Response.ok(Finder.get_all_medication(medication).toString()).build();
	}
	
	/**
	 * Elimina un medicamento.
	 * @param name el nombre del medicamento.
	 * @return Mensaje de verificación.
	 */
	@DELETE
	@Path("/medication/{name}")
	public Response delete_medication(@PathParam("name") String name){
		medication.remove(Finder.find_medication(name, medication));
		return Response.ok("Medication removed!").build();
	}
	
	/**
	 * Edita los detalles de un medicamento.
	 * @param info la información actualizada.
	 * @param name el nombre dle medicamento.
	 * @return Mensaje de verificación.
	 */
	@PUT
	@Path("/medication/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response edit_medication(String info, @PathParam("name") String name){
		Medication m = Finder.find_medication(name, medication);
		JSONObject json_test = JSONHandler.parse(info);
		m.edit_name(json_test.getString("name"));
		m.edit_cost(json_test.getInt("cost"));
		return Response.ok("Medication updated!").build();
	}
	
	/**
	 * @param name el nombre del medicamento.
	 * @return los detalles de la medicación.
	 */
	@GET
	@Path("/medication/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_medication_details(@PathParam("name") String name){
		return Response.ok(JSONHandler.build_medication_details(Finder.find_medication(name, medication))).build();
	}
	
	/**
	 * Crea un nuevo medicamento.
	 * @param new_medication la información dle medicamento.
	 * @return Mensaje de verificación.
	 */
	@POST
	@Path("/medication/new_medication")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create_medication(String new_medication){
		Medication medication = JSONHandler.parse_new_medication(new_medication);
		XMLHandler.write_medication(medication.name(), String.valueOf(medication.price()), String.valueOf(medication.id()));
		this.medication.insert(medication);
		return Response.ok(medication.name() + " created!").build();		
	}
	
	/**
	 * @param medic_code la identificación del médico.
	 * @return los comentarios del médico.
	 */
	@GET
	@Path("/{id}/feedback")
	public Response get_feedback(@PathParam("id") String medic_code){
		Medic medic = Finder.find_medic_by_code(medic_code);
		return Response.ok(medic.get_comments()).build();
	}
	
	/**
	 * Procesa un nuevo médico.
	 * @param medic el médico  nuevo.
	 */
	private void process_medic(Medic medic){
		medic_tree.insert(medic, IdentifiersGenerator.generate_new_key(3));
	}
	
	private void create_dummy_medics(){
		
		Medic a = new Medic("Alonso", "some@email.com");
		Medic b = new Medic("Bryan", "some@email.com");
		Medic c = new Medic("Jennifer", "some@email.com");
		Medic d = new Medic("Fabian", "some@email.com");
		Medic e = new Medic("Kate", "some@email.com");
		
		medic_tree.insert(a, IdentifiersGenerator.generate_new_key(3));
		medic_tree.insert(b, IdentifiersGenerator.generate_new_key(3));
		medic_tree.insert(c, IdentifiersGenerator.generate_new_key(3));
		medic_tree.insert(d, IdentifiersGenerator.generate_new_key(3));
		medic_tree.insert(e, IdentifiersGenerator.generate_new_key(3));
	}
	

}
