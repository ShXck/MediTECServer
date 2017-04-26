package com.meditec.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.meditec.datastructures.List;
import com.meditec.datastructures.SplayTree;
import com.meditec.medmanagement.Medic;
import com.meditec.utilities.XMLHandler;
import com.meditec.medmanagement.Finder;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Path("/medics")
public class MedicResources {
	
	private SplayTree<Medic> medic_tree = new SplayTree<>();
	private int key_values = 0;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response log_medic(String json_info){
		
		JSONObject medic_json = new JSONObject(json_info);
		
		Medic medic = new Medic(medic_json.getString("name"), medic_json.getString("email"));
		process_medic(medic);
		
		return Response.ok("Welcome!").build();
	}
	
	private void process_medic(Medic medic){
		if (!Finder.find_medic_by_name(medic.name())) {
			Finder.register(medic.name(),"medic");
			medic_tree.insert(medic,key_values + 1);
			XMLHandler.serialize_medic(medic);
		}
	}

}
