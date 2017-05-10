package com.meditec.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.meditec.clientmanagement.Mailer;
import com.meditec.clientmanagement.Patient;
import com.meditec.datastructures.AVLTree;
import com.meditec.medmanagement.Appointment;
import com.meditec.medmanagement.Finder;
import com.meditec.medmanagement.Medic;
import com.meditec.utilities.JSONHandler;

@Path("/patient")
public class PatientResources {
	
	public static AVLTree<Patient>  patients_tree = new AVLTree<>();
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response log_patient(String json_info){
		
		JSONObject patient_info = new JSONObject(json_info);
	
		try{
			Patient p = Finder.find_patient(patient_info.getString("name"));
		}catch (NullPointerException e) {
			Patient patient = new Patient(patient_info.getString("name"), patient_info.getString("email"));
			process_client(patient);
			return Response.ok("{status:blocked}").build();
		}
		return Response.ok("{status:unblocked}").build();
	}
	
	@POST
	@Path("/book")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response book_appointment(String json_appointment){
	
		JSONObject appointment = new JSONObject(json_appointment);
		
		Appointment new_appointment = new Appointment(appointment.getInt("year"), appointment.getInt("month"), appointment.getInt("day"), appointment.getString("patient"), appointment.getString("code"));
		
		Medic medic = Finder.find_medic_by_code(appointment.getString("code"));
		medic.agenda().schedule_appointment(new_appointment);
		
		Patient patient = Finder.find_patient(appointment.getString("patient"));
		patient.set_current_appointment(new_appointment);
		patient.set_last_appointment(new_appointment);
		
		Mailer.send_appointment_email(medic.email());
		
		return Response.ok("Your Appointment is set").build();
	}
	
	@GET
	@Path("/medics_list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_medics_list(){
		return Response.ok(Finder.get_all_medics().toString()).build();
	}
	
	@GET
	@Path("/{patient}/appointments")
	public Response get_appointment_detail(@PathParam("patient") String patient_name){
		Patient p = Finder.find_patient(patient_name);
		return Response.ok(JSONHandler.get_appointment_overview(p.current_appointment())).build();
	}
	
	@DELETE
	@Path("/{patient}/appointments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete_appointment(String med_code, @PathParam("patient") String patient_name){
		Patient patient = Finder.find_patient(patient_name);
		patient.set_current_appointment(null);
		patient.set_last_appointment(null);
		Medic medic = Finder.find_medic_by_code(JSONHandler.get_code(med_code));
		medic.agenda().remove_appointment(patient_name);
		return Response.ok("Your appointment has been cancelled").build();
	}
	
	@DELETE
	@Path("/{patient}/pay")
	public Response pay_appointment(@PathParam("patient") String patient_name){
		Patient patient = Finder.find_patient(patient_name);
		patient.set_current_appointment(null);
		return Response.ok("Appointmetn paid!").build();
	}
	
	@POST
	@Path("{id}/rate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response get_feedback(String feedback, @PathParam("id") String name){
		System.out.println("hola");
		JSONObject json_info = new JSONObject(feedback);
		Medic medic = Finder.find_medic_by_code(json_info.getString("code"));
		medic.add_comments(json_info.getString("comments"));
		Patient patient = Finder.find_patient(name);
		patient.set_last_appointment(null);
		return Response.ok("Your comments were added succesfully!").build();
	}
	
	@GET
	@Path("/{id}/appointments/last")
	public Response get_last_appointment_info(@PathParam("id") String patient_name){
		Patient patient = Finder.find_patient(patient_name);
		return Response.ok(JSONHandler.get_appointment_overview(patient.last_appointment())).build();
	}
	
	private void process_client(Patient p){
		patients_tree.insert(p);
		Mailer.send_qr(p.email(), p.name());
	}
}

