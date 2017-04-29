package com.meditec.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import com.meditec.datastructures.SplayTree;
import com.meditec.medmanagement.Medic;
import com.meditec.utilities.JSONHandler;
import com.meditec.utilities.XMLHandler;
import com.meditec.medmanagement.Appointment;
import com.meditec.medmanagement.Finder;

@Path("/medics")
public class MedicResources {
	
	public static SplayTree<Medic> medic_tree = new SplayTree<>();
	private int key_values = 1;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response log_medic(String json_info){
		
		JSONObject medic_json = new JSONObject(json_info);
		
		create_dummy_medics();
		
		try{
			Medic m = Finder.find_medic_by_name(medic_json.getString("name"));
		}catch (NullPointerException e) {
			Medic new_medic = new Medic(medic_json.getString("name"), medic_json.getString("email"));
			process_medic(new_medic);
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
		Medic medic = Finder.find_medic_by_code(id);
		Appointment appointment = medic.agenda().get_appointment_info(patient);
		//TODO: Modify appointment with the information given in the dr app.
		medic.agenda().edit_appointment_info(appointment, new Appointment(0, 0, 0, patient));
	}
	
	
	private void process_medic(Medic medic){
		medic_tree.insert(medic,key_values);
		key_values++;
		XMLHandler.serialize_medic(medic);
	}
	
	private void create_dummy_medics(){
		Medic a = new Medic("Alonso", "some@email.com");
		Medic b = new Medic("Bryan", "some@email.com");
		Medic c = new Medic("Jennifer", "some@email.com");
		Medic d = new Medic("Fabian", "some@email.com");
		Medic e = new Medic("Kate", "some@email.com");
		
		medic_tree.insert(a, key_values);
		key_values++;
		medic_tree.insert(b, key_values);
		key_values++;
		medic_tree.insert(c, key_values);
		key_values++;
		medic_tree.insert(d, key_values);
		key_values++;
		medic_tree.insert(e, key_values);
		key_values++;
	}

}
