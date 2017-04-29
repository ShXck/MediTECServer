package com.meditec.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.meditec.clientmanagement.Mailer;
import com.meditec.clientmanagement.Patient;
import com.meditec.datastructures.AVLTree;
import com.meditec.datastructures.List;
import com.meditec.medmanagement.Appointment;
import com.meditec.medmanagement.Finder;
import com.meditec.medmanagement.Medic;
import com.meditec.utilities.XMLHandler;
import com.sun.mail.imap.protocol.Status;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.tracing.dtrace.ProviderAttributes;

@Path("/patient")
public class PatientResources {
	
	public static AVLTree<Patient>  patients_tree = new AVLTree<>();
	Mailer Mailer = new Mailer();
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response log_patient(String json_info){
		
		JSONObject patient_info = new JSONObject(json_info);
		
		Patient patient = new Patient(patient_info.getString("name"), patient_info.getString("email"));
	
		try{
			Patient p = Finder.find_patient(patient.name());
		}catch (NullPointerException e) {
			process_client(patient);
			return Response.ok("Welcome to MediTEC " + patient.name()).build();
		}
		
		return Response.ok("Welcome Back " + patient.name()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/book")
	public Response book_appointment(String json_appointment){
		
		System.out.println(json_appointment);
		
		JSONObject appointment = new JSONObject(json_appointment);
		
		Appointment new_appointment = new Appointment(appointment.getInt("year"), appointment.getInt("month"), appointment.getInt("day"), appointment.getString("patient"));
		
		Medic medic = Finder.find_medic_by_code(appointment.getString("code"));
		medic.agenda().schedule_appointment(new_appointment);
		
		return Response.ok("Your Appointment is set").build();
	}
	
	@GET
	@Path("/medics_list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get_medics_list(){
		return Response.ok(Finder.get_all_medics().toString()).build();
	}
	
	private void process_client(Patient p){
		patients_tree.insert(p);
		XMLHandler.serialize_patient(p);
		//Mailer.send_qr(p.email(), p.name());
	}
}

