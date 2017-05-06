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
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response log_medic(String json_info){
		
		JSONObject medic_json = new JSONObject(json_info);
		
		try{
			Medic m = Finder.find_medic_by_name(medic_json.getString("name"));
		}catch (NullPointerException e) {
			Medic new_medic = new Medic(medic_json.getString("name"), medic_json.getString("email"));
			create_dummy_medics();
			process_medic(new_medic);
			XMLHandler.add_cases_to_tree(cases);
			XMLHandler.add_tests_to_tree(tests);
			XMLHandler.add_medication_to_tree(medication);
			return Response.ok(JSONHandler.get_identifier(new_medic.code())).build();
		}
		
		Medic medic = Finder.find_medic_by_name(medic_json.getString("name"));
		
		return Response.ok(JSONHandler.get_identifier(medic.code())).build();
		
	}
	
	@GET
	@Path("/{id}/appointments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_medic_appointments(@PathParam("id") String id){
		JSONObject appointments = Finder.get_medic_appointments(id);
		return Response.ok(appointments.toString()).build();
	}
	
	@GET
	@Path("/{id}/appointments/{patient}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_client_appointment(@PathParam("id") String id, @PathParam("patient") String patient_name){
		Medic medic = Finder.find_medic_by_code(id);
		Appointment appointment = medic.agenda().get_appointment_info(patient_name);
		return Response.ok(JSONHandler.get_json_appointment(appointment)).build();
	}
	
	@PUT
	@Path("/{id}/appointments/{patient}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response edit_appointment_info(String updated_info, @PathParam("id") String id, @PathParam("patient") String patient){
		System.out.println(updated_info);
		Medic medic = Finder.find_medic_by_code(id);
		Appointment appointment = medic.agenda().get_appointment_info(patient);
		medic.agenda().edit_appointment(JSONHandler.get_case_name(updated_info), JSONHandler.get_updated_symptoms(updated_info), JSONHandler.get_updated_medication(updated_info), JSONHandler.get_updated_tests(updated_info), JSONHandler.get_updated_cases(updated_info), appointment);
		return Response.ok("Appointment edited!").build();
	}
	
	@POST
	@Path("/cases/new_case")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create_clinic_case(String case_info){
		System.out.println(case_info);
		ClinicCase new_case = JSONHandler.parse_new_clinic_case(case_info);
		cases.insert(new_case.key(), new_case);
		return Response.ok("Clinic case succesfully created").build();
	}
	
	@GET
	@Path("/cases")
	public Response get_cases_list() {
		return Response.ok(Finder.get_all_cases(cases).toString()).build();
	}
	
	@DELETE
	@Path("/cases/{name}")
	public Response delete_clinic_case(@PathParam("name") String case_name) {
		cases.remove(Finder.find_case(case_name).key());
		return Response.ok("Case removed successfully").build();
	}
	
	@GET
	@Path("/cases/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_case_details(@PathParam("name") String name) {
		return Response.ok(JSONHandler.parse_clinic_case(Finder.find_case(name))).build();
	}
	
	@PUT
	@Path("/cases/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response edit_case_details(String json_details, @PathParam("name") String case_name){
		ClinicCase clinic_case = Finder.find_case(case_name);
		JSONObject details = new JSONObject(json_details);
		clinic_case.edit_case(details.getString("tests"), details.getString("medication"));
		return Response.ok("Clinic Case Edited!").build();
	}
	
	@GET
	@Path("/tests")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_all_tests(){
		return Response.ok(Finder.get_all_tests(tests).toString()).build();
	}
	
	@POST
	@Path("/tests/new_test")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create_new_test(String new_test){
		MedicTest medic_test = JSONHandler.build_new_test(new_test);
		tests.insert(medic_test, medic_test.id());
		return Response.ok("Test created succesfully").build();
	}
	
	@DELETE
	@Path("/tests/{name}")
	public Response delete_test(@PathParam("name") String name){
		tests.remove(Finder.find_test(name, tests).id());
		return Response.ok("Test " + name + " eliminated succesfully").build();
		
	}
	
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
	
	@GET
	@Path("/tests/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_test_details(@PathParam("name") String name){
		return Response.ok(JSONHandler.build_test_details(Finder.find_test(name, tests))).build();
	}
	
	@GET
	@Path("/medication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_all_medication(){
		return Response.ok(Finder.get_all_medication(medication).toString()).build();
	}
	
	@DELETE
	@Path("/medication/{name}")
	public Response delete_medication(@PathParam("name") String name){
		medication.remove(Finder.find_medication(name, medication));
		return Response.ok("Medication removed!").build();
	}
	
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
	
	@GET
	@Path("/medication/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_medication_details(@PathParam("name") String name){
		return Response.ok(JSONHandler.build_medication_details(Finder.find_medication(name, medication))).build();
	}
		
	@POST
	@Path("/medication/new_medication")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create_medication(String new_medication){
		Medication medication = JSONHandler.build_new_medication(new_medication);
		this.medication.insert(medication);
		return Response.ok(medication.name() + " created!").build();		
	}
	

	
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
