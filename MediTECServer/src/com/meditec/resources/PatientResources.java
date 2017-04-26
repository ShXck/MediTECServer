package com.meditec.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.meditec.clientmanagement.Patient;
import com.meditec.clientmanagement.QRManager;
import com.meditec.datastructures.AVLTree;
import com.meditec.datastructures.List;
import com.meditec.medmanagement.Finder;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.tracing.dtrace.ProviderAttributes;

@Path("/patient")
public class PatientResources {
	
	private AVLTree<Patient>  patients_tree = new AVLTree<>();
	QRManager qr_manager = new QRManager();
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(JSONObject json_info){
		
		JSONObject patient_info = new JSONObject(json_info);
		
		Patient new_patient = new Patient(patient_info.getString("name"), patient_info.getString("email"));
		process_client(new_patient);
		
		return Response.ok("Patient added succesfully").build();
	}
	
	private void process_client(Patient p){
		if (!Finder.find_patient(p.name())) {
			Finder.register(p.name(),"patient");
			patients_tree.insert(p);
			//qr_manager.send_qr(p.email(), p.name());
		}
	}

}
