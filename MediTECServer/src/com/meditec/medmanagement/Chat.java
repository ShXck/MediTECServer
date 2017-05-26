package com.meditec.medmanagement;

import org.json.JSONArray;
import org.json.JSONObject;

import com.meditec.datastructures.List;
import com.meditec.datastructures.Node;

public class Chat {
	
	private static List<String> messages = new List<>();
	
	/**
	 * @return la lista de mensajes.
	 */
	public static List<String> get_messages_list(){
		return messages;
	}
	
	/**
	 * 
	 * @return un objeto json con los mensajes.
	 */
	public static JSONObject get_messages(){
		JSONObject json_messages = new JSONObject();
		JSONArray array = new JSONArray();
		
		Node<String> current = messages.peek();
		
		while (current != null) {
			array.put(current.data());
			current = current.next();
		}
		json_messages.put("messages", array);
		return json_messages;
	}
	
	
	public static void create_message(String new_message, String name){
		messages.add_last(new_message + " - " + name);
	}
	


}
