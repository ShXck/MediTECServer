package com.meditec.medmanagement;

import org.json.JSONObject;
import com.meditec.clientmanagement.Patient;
import com.meditec.datastructures.AVLNode;
import com.meditec.datastructures.AVLTree;
import com.meditec.datastructures.BinaryTree;
import com.meditec.datastructures.SplayNode;
import com.meditec.datastructures.SplayTree;
import com.meditec.datastructures.TreeNode;
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
	
	public static ClinicCase find_case(String name, BinaryTree<ClinicCase> tree){
		return find_case(name.toLowerCase(), tree.root()).data();
	}
	
	private static TreeNode<ClinicCase> find_case(String name, TreeNode<ClinicCase> node){
		if (node != null) {
			if (node.data().name().toLowerCase().equals(name.toLowerCase())) {
				return node;
			}else {
				TreeNode<ClinicCase> tmp = find_case(name, node.get_left());
				if (tmp == null) {
					tmp = find_case(name, node.get_right());
				}
				return tmp;
			}
		}else {
			return null;
		}
	}
	
	public static String get_medication_list(AVLTree<Medication> tree){
		return get_medication_list(tree.root(), "");
	}
	
	private static String get_medication_list(AVLNode<Medication> node, String result){
		if (node == null) {
			return "";
		}
		result += get_medication_list(node.left(), result);
		result += get_medication_list(node.right(), result);
		result += node.data().name() + ",";
		return result;
	}
	
	public static String get_tests_list(SplayTree<MedicTest> tree){
		return get_tests_list(tree.root(), "");
	}
	
	private static String get_tests_list(SplayNode<MedicTest> node, String result){
		if (node == null) {
			return "";
		}
		result += get_tests_list(node.left, result);
		result += get_tests_list(node.right,result);
		result += node.element.name() + ",";
		return result;
	}
	
	public static JSONObject get_all_cases(BinaryTree<ClinicCase> tree){
		return get_all_cases(tree.root(), new JSONObject(), 1);
	}
	
	private static JSONObject get_all_cases(TreeNode<ClinicCase> node, JSONObject json, int count){
		if (node != null) {
			System.out.println(node.data().name());
			get_all_cases(node.get_left(), json , count + 1);
			json.put(String.valueOf(count), node.data().name());
			json.put("count", count);
			get_all_cases(node.get_right(), json, count + 1);
		}
		return json;
	}
	
	public static Medication find_medication(String name, AVLTree<Medication> tree){
		return find_medication(name, tree.root()).data();
	}
	
	private static AVLNode<Medication> find_medication(String name, AVLNode<Medication> node){
		if (node != null) {
			if (node.data().name().toLowerCase().equals(name.toLowerCase())) {
				return node;
			}else {
				AVLNode<Medication> tmp = find_medication(name, node.left());
				if (tmp == null) {
					tmp = find_medication(name, node.right());
				}
				return tmp;
			}
		}else {
			return null;
		}
	}
	
	public static MedicTest find_test(String name, SplayTree<MedicTest> tree){
		return find_test(name, tree.root()).element;
	}
	
	private static SplayNode<MedicTest> find_test(String name, SplayNode<MedicTest> node){
		if (node != null) {
			if (node.element.name().toLowerCase().equals(name.toLowerCase())) {
				return node;
			}else {
				SplayNode<MedicTest> tmp = find_test(name, node.left);
				if (tmp == null) {
					tmp = find_test(name, node.right);
				}
				return tmp;
			}
		}else {
			return null;
		}
	}
	
	public static JSONObject get_all_tests(SplayTree<MedicTest> tree){
		return get_all_tests(tree.root(), new JSONObject(), 1);
	}
	
	private static JSONObject get_all_tests(SplayNode<MedicTest> node, JSONObject json, int count){
		if (node != null) {
			get_all_tests(node.left, json , count + 1);
			json.put(String.valueOf(count), node.element.name());
			json.put("count", count);
			get_all_tests(node.right, json, count + 1);
		}
		return json;
	}
	
	public static JSONObject get_all_medication(AVLTree<Medication> tree){
		return get_all_medication(tree.root(), new JSONObject(), 1);
	}
	
	private static JSONObject get_all_medication(AVLNode<Medication> node, JSONObject json, int count){
		if (node != null) {
			get_all_medication(node.left(), json , count + 1);
			json.put(String.valueOf(count), node.data().name());
			get_all_medication(node.right(), json, count + 1);
		}
		json.put("count", MedicResources.medication.count());
		return json;
	}

	public static String get_cases_list(BinaryTree<ClinicCase> tree){
		return get_cases_list(tree.root(), "");
	}
	
	private static String get_cases_list(TreeNode<ClinicCase> node, String result){
		if (node == null) {
			return "";
		}
		result += get_cases_list(node.get_left(), result);
		result += get_cases_list(node.get_right(),result);
		result += node.data().name() + ",";
		return result;
	}
	
	public static int find_appointment_cost(BinaryTree<ClinicCase> cases_tree){
		return find_appointment_cost(cases_tree.root(),0);
	}
	
	private static int find_appointment_cost(TreeNode<ClinicCase> node, int total){
		if (node == null) {
			return 0;
		}
		total += find_appointment_cost(node.get_left(), total);
		total += find_appointment_cost(node.get_right(),total);
		total += node.data().price();
		return total;
	}
	
	public static boolean contains_medication(String name, BinaryTree<ClinicCase> tree){
		return contains_medication(name, tree.root());
	}
	
	private static boolean contains_medication(String name, TreeNode<ClinicCase> node){
		if (node == null) {
			return false;
		}else {
			try{
				Medication m = find_medication(name, node.data().medication());
				return true;
			}catch (NullPointerException e) {
				if (node.get_left() != null) {
					return contains_medication(name, node.get_left());
				}
				if (node.get_right() != null) {
					return contains_medication(name, node.get_right());
				}
			}
		}
		return false;
	}
}
