package com.meditec.medmanagement;

import com.meditec.datastructures.List;
import com.meditec.utilities.IdentifiersGenerator;
import com.meditec.utilities.JSONHandler;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Medic implements Comparable<Medic>{
	
	private String code;
	
	private Agenda agenda;
	
	private String name;
	
	private String email;
	
	private List<String> comments;
	
	public Medic(String name, String email){
		this.code = IdentifiersGenerator.generate_new_code(4);
		this.agenda = new Agenda();
		this.name = name;
		this.email = email;
		this.comments = new List<>();
	}

	public String code() {
		return code;
	}

	public Agenda agenda() {
		return agenda;
	}

	public String name() {
		return name;
	}

	public String email() {
		return email;
	}
	
	public void add_comments(String comments){
		String[] comments_list = comments.split(",");
		
		for(int i = 0; i < comments_list.length; i++){
			this.comments.add_last(comments_list[i]);
		}
	}
	
	public String get_comments(){
		return JSONHandler.get_json_comments(comments);
	}

	@Override
	public int compareTo(Medic o) {
		if (o.code.equals(code)) {
			return 0;
		}else {
			return -1;
		}
	}
	
	

}
