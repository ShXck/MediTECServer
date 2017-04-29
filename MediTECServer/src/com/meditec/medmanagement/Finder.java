package com.meditec.medmanagement;

import org.glassfish.jersey.jaxb.internal.XmlCollectionJaxbProvider.App;
import org.json.JSONObject;

import com.meditec.clientmanagement.Patient;
import com.meditec.datastructures.AVLNode;
import com.meditec.datastructures.AVLTree;
import com.meditec.datastructures.List;
import com.meditec.datastructures.Node;
import com.meditec.datastructures.SplayNode;
import com.meditec.resources.MedicResources;
import com.meditec.resources.PatientResources;

public class Finder {
	
	public static JSONObject get_all_medics(){
		return get_all_medics(MedicResources.medic_tree.root(), new JSONObject(), 1);
	}
	
	private static JSONObject get_all_medics(SplayNode<Medic> node, JSONObject json, int count){
		if (node != null) {
			get_all_medics(node.left, json, count + 1);
			json.put(String.valueOf(count), node.element.code());
			get_all_medics(node.right, json, count + 1);
		}
		json.put("count", MedicResources.medic_tree.countNodes());
		return json;
	}

	public static Medic find_medic_by_name(String name){
        return find_medic_by_name(MedicResources.medic_tree.root(), name).element;
    }

    private static SplayNode<Medic> find_medic_by_name(SplayNode<Medic> node, String name){
        if (node != null){
            if (node.element.name().equals(name)){
                return node;
            }else {
                SplayNode<Medic> temp = find_medic_by_name(node.left, name);
                if (temp == null){
                    temp = find_medic_by_name(node.right,name);
                }
                return temp;
            }
        }else {
            return null;
        }
    }

    public static Medic find_medic_by_code(String code){
        return find_medic_by_code(MedicResources.medic_tree.root(),code).element;
    }

    private static SplayNode<Medic> find_medic_by_code(SplayNode<Medic> node, String code) {

        if (node != null){
            if (node.element.code().equals(code)){
                return node;
            }else {
                SplayNode<Medic> temp = find_medic_by_code(node.left, code);
                if (temp == null){
                    temp = find_medic_by_code(node.right,code);
                }
                return temp;
            }
        }else {
            return null;
        }
    }
	
	public static Patient find_patient(String name){
		return find_patient(PatientResources.patients_tree.root(), name).data();
	}
	
	private static 	AVLNode<Patient> find_patient(AVLNode<Patient> node, String name){
		
		 if (node != null){
	            if (node.data().name().equals(name)){
	                return node;
	            }else {
	                AVLNode<Patient> temp = find_patient(node.left(), name);
	                if (temp == null){
	                    temp = find_patient(node.right(),name);
	                }
	                return temp;
	            }
	        }else {
	            return null;
	        }
	}
	
	public static JSONObject get_medic_appointments(String id){
		Medic medic = find_medic_by_code(id);
		return get_medic_appointments(medic.agenda().appointments().root(), new JSONObject(), 1, medic);
	
	}
	
	private static JSONObject get_medic_appointments(AVLNode<Appointment> node, JSONObject json, int count, Medic medic){
		if (node != null) {
			get_medic_appointments(node.left(), json, count + 1,medic);
			json.put(String.valueOf(count), node.data().patient());
			get_medic_appointments(node.right(), json, count + 1,medic);
		}
		json.put("count", count);
		return json;
	}
	
	public static Appointment get_appointment(String name, AVLTree<Appointment> tree){
		return get_appointment(tree.root(), name).data();
	}
	
	private static AVLNode<Appointment> get_appointment(AVLNode<Appointment> node, String name){
		if (node != null) {
			if (node.data().patient().equals(name)) {
				return node;
			}else{
				AVLNode<Appointment> tmp = get_appointment(node.left(), name);
				if (tmp == null) {
					tmp = get_appointment(node.right(), name);
				}
				return tmp;
			}
		}else {
			return null;
		}
	}
}
