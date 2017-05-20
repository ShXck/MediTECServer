package com.meditec.medmanagement;

import org.json.JSONArray;
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
	
	/**
	 * @return al lista de médicos.
	 */
	public static JSONObject get_all_medics(){
		JSONObject medics = new JSONObject();
		medics.put("medics", get_all_medics(MedicResources.medic_tree.root(), new JSONArray()));
		return medics;
	}
	
	/**
	 * Construye el arreglo de médicos.
	 * @param node el nodo inicial.
	 * @param json el array donde se guardan los médicos.
	 * @return el arreglo de médicos final.
	 */
	private static JSONArray get_all_medics(SplayNode<Medic> node, JSONArray json){
		if (node != null) {
			get_all_medics(node.left, json);
			json.put(node.element.code());
			get_all_medics(node.right, json);
		}
		return json;
	}
	
	/**
	 * @param tree el arbol de los medicos
	 * @return un json con los nombres de los médicos.
	 */
	public static JSONObject get_all_medic_names(SplayTree<Medic> tree){
		JSONObject medics = new JSONObject();
		medics.put("medics", get_all_medic_names(tree.root(), new JSONArray()));
		return medics;
	}
	
	/**
	 * 
	 * @param node el nodo inicial.
	 * @param array el array donde se guardan los nombres.
	 * @return el array con los nombres.
	 */
	private static JSONArray get_all_medic_names(SplayNode<Medic>node, JSONArray array){
		if (node != null) {
			get_all_medics(node.left, array);
			array.put(node.element.name());
			get_all_medics(node.right, array);
		}
		return array;
	}
	
	/**
	 * Encuentra aa un médico por el nombre.
	 * @param name el nombre del médico.
	 * @return el médico que corresponde con el nombre.
	 */
	public static Medic find_medic_by_name(String name){
        return find_medic_by_name(MedicResources.medic_tree.root(), name).element;
    }
	
	/**
	 * @param node el nodo incial.
	 * @param name el nombre del médico.
	 * @return el nodo que contiene al médico.
	 */
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
    
    /**
     * Encuentra a un médico por el código.
     * @param code el código del médico.
     * @return el médico que corresponde con el código.
     */
    public static Medic find_medic_by_code(String code){
        return find_medic_by_code(MedicResources.medic_tree.root(),code).element;
    }
    
    /**
     * Método auxiliar para encontrar un médico por código.
     * @param node el nodo inicial.
     * @param code el código del médico.
     * @return el nodo que contiene al médico.
     */
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
	
    /**
     * Encuentra un paciente por nombre.
     * @param name el nombre del paciente.
     * @return El objeto paciente.
     */
	public static Patient find_patient(String name){
		return find_patient(PatientResources.patients_tree.root(), name).data();
	}
	
	/**
	 * Método auxiliar para encontrar pacientes por nombre.
	 * @param node el nodo inicial.
	 * @param name el nombre del paciente.
	 * @return el nodo que contiene al paciente.
	 */
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
	
	/**
	 * @param id la id del médico.
	 * @return objeto json con todas las citas del médico.
	 */
	public static JSONObject get_medic_appointments(String id){
		Medic medic = find_medic_by_code(id);
		JSONObject appointments = new JSONObject();
		appointments.put("appointments", get_medic_appointments(medic.agenda().appointments().root(), medic, new JSONArray()));
		return appointments;
	}
	
	/**
	 * Método auxiliar para obtener todas las citas del médico.
	 * @param node el nodo inicial.
	 * @param medic el médico.
	 * @param array el array donde se meten las citas.
	 * @return un array con las citas.
	 */
	private static JSONArray get_medic_appointments(AVLNode<Appointment> node, Medic medic, JSONArray array){
		if (node != null) {
			get_medic_appointments(node.left(), medic, array);
			array.put(node.data().patient());
			get_medic_appointments(node.right(), medic, array);
		}
		return array;
	}
	
	/**
	 * @param name el nombre del paciente.
	 * @param tree el arbol de citas.
	 * @return la cita que coincide con el nombre.
	 */
	public static Appointment get_appointment(String name, AVLTree<Appointment> tree){
		return get_appointment(tree.root(), name).data();
	}
	
	/**
	 * El método auxiliar para encontrar una cita.
	 * @param node el nodo inicial.
	 * @param name el nombre del paciente.
	 * @return el nodo que contiene la cita.
	 */
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
	
	/**
	 * Encuentra un caso por nombre.
	 * @param name el nombre del caso.
	 * @param tree el arbol de casos.
	 * @return el caso.
	 */
	public static ClinicCase find_case(String name, BinaryTree<ClinicCase> tree){
		return find_case(name.toLowerCase(), tree.root()).data();
	}
	
	/**
	 * metodo auxiliar para encontrar un caso.
	 * @param name el nombre del caso.
	 * @param node el nodo inicial.
	 * @return el nodo que contiene el caso.
	 */
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
	
	/**
	 * 
	 * @param tree el arbol de medicamentos.
	 * @return lista de medicamentos en string.
	 */
	public static String get_medication_list(AVLTree<Medication> tree){
		return get_medication_list(tree.root(), "");
	}
	
	/**
	 * método auxiliar para armar lista de medicamentos.
	 * @param node el nodo inicial.
	 * @param string donde se arma la lista.
	 * @return el resultado final.
	 */
	private static String get_medication_list(AVLNode<Medication> node, String result){
		if (node == null) {
			return "";
		}else {
			result += get_medication_list(node.left(), result);
			result += get_medication_list(node.right(), result);
			result += node.data().name() + ",";
		}
		return result;
		
	}
	
	/**
	 * @param tree el arbol de exámenes médicos.
	 * @return un string con la lista de exámenes.
	 */
	public static String get_tests_list(SplayTree<MedicTest> tree){
		return get_tests_list(tree.root(), "");
	}
	
	/**
	 * método auxiliar para armar la lista de exámenes médicos.
	 * @param node el nodo inicial.
	 * @param result string donde se arma el resultado.
	 * @return la lista armada de exámenes.
	 */
	private static String get_tests_list(SplayNode<MedicTest> node, String result){
		if (node == null) {
			return "";
		}else {
			result += get_tests_list(node.left, result);
			result += get_tests_list(node.right,result);
			result += node.element.name() + ",";
		}
		return result;
	}
	
	/**
	 * @param tree el arbol de casos.
	 * @return objeto json con todos los casos.
	 */
	public static JSONObject get_all_cases(BinaryTree<ClinicCase> tree){
		JSONObject cases = new JSONObject();
		cases.put("cases", get_all_cases(tree.root(), new JSONArray()));
		return cases;
	}
	
	/**
	 * método auxiliar para obtener todos los casos.
	 * @param node el nodo inicial.
	 * @param json el json donde se arma.
	 * @return array con todos los casos.
	 */
	private static JSONArray get_all_cases(TreeNode<ClinicCase> node, JSONArray json){
		if (node != null) {
			get_all_cases(node.get_left(), json);
			json.put(node.data().name());
			get_all_cases(node.get_right(), json);
		}
		return json;
	}
	
	/**
	 * Encuentra medicación por nombre.
	 * @param name el nombre de la medicación.
	 * @param tree el árbol de medicación.
	 * @return el objeto medicación que coincide con el nombre.
	 */
	public static Medication find_medication(String name, AVLTree<Medication> tree){
		return find_medication(name, tree.root()).data();
	}
	
	/**
	 * Método auxiliar para encontrar medicación por nombre.
	 * @param name el nombre de la medicación.
	 * @param node el nodo inicial.
	 * @return el nodo que contiene la medicación.
	 */
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
	
	/**
	 * Encuentra un examen por nombre.
	 * @param name el nombre del exámen.
	 * @param tree el árbol de exámenes.
	 * @return el objeto exámen.
	 */
	public static MedicTest find_test(String name, SplayTree<MedicTest> tree){
		return find_test(name, tree.root()).element;
	}
	
	/**
	 * Método auxiliar para encontrar un exámen médico.
	 * @param name el  nombre del examen.
	 * @param node el nodo inicial.
	 * @return el nodo que contiene el exámen.
	 */
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
	
	/**
	 * @param tree el arbol de exámenes.
	 * @return objeto json con todos los exámenes.
	 */
	public static JSONObject get_all_tests(SplayTree<MedicTest> tree){
		JSONObject tests = new JSONObject();
		tests.put("tests", get_all_tests(tree.root(), new JSONArray()));
		return tests;
	}
	
	/**
	 * Método auxiliar para obtener todos los exámenes.
	 * @param node el nodo inicial.
	 * @param json el array donde se meten los exámenes.
	 * @return el array con los exámenes.
	 */
	private static JSONArray get_all_tests(SplayNode<MedicTest> node, JSONArray json){
		if (node != null) {
			get_all_tests(node.left, json);
			json.put(node.element.name());
			get_all_tests(node.right, json);
		}
		return json;
	}
	
	/**
	 * @param tree el arbol de medicación.
	 * @return objeto json con todos los medicamentos.
	 */
	public static JSONObject get_all_medication(AVLTree<Medication> tree){
		JSONObject medication = new JSONObject();
		medication.put("medication", get_all_medication(tree.root(), new JSONArray()));
		return medication;
	}
	
	/**
	 * Método auxiliar para encontrar todos los medicamentos.
	 * @param node el nodo inicial.
	 * @param json el array donde se arman los medicamentos.
	 * @return el array con todos los medicamentos.
	 */
	private static JSONArray get_all_medication(AVLNode<Medication> node, JSONArray json){
		if (node != null) {
			get_all_medication(node.left(), json);
			json.put(node.data().name());
			get_all_medication(node.right(), json);
		}
		return json;
	}
	
	/**
	 * @param tree el árbol de casos.
	 * @return lista en string de todos los casos.
	 */
	public static String get_cases_list(BinaryTree<ClinicCase> tree){
		return get_cases_list(tree.root(), "");
	}
	
	/**
	 * Método auxiliar para obtener una lista de casos en string.
	 * @param node el nodo inicial.
	 * @param result donde se arma la lista.
	 * @return la lista en string con todos los casos.
	 */
	private static String get_cases_list(TreeNode<ClinicCase> node, String result){
		if (node == null) {
			return "";
		}
		result += get_cases_list(node.get_left(), result);
		result += get_cases_list(node.get_right(),result);
		result += node.data().name() + ",";
		return result;
	}
	
	/**
	 * Encuentra el costo de una cita.
	 * @param cases_tree el árbol de casos.
	 * @return el costo de la cita.
	 */
	public static int find_appointment_cost(BinaryTree<ClinicCase> cases_tree){
		return find_appointment_cost(cases_tree.root(),0);
	}
	
	/**
	 * Método auxiliar para encontrar el costo de la cita.
	 * @param node el nodo inicial.
	 * @param total la suma.
	 * @return el total de la suma.
	 */
	private static int find_appointment_cost(TreeNode<ClinicCase> node, int total){
		if (node == null) {
			return 0;
		}
		total += find_appointment_cost(node.get_left(), total);
		total += find_appointment_cost(node.get_right(),total);
		total += node.data().price();
		return total;
	}
	
	/**
	 * @param name el nombre de la medicación.
	 * @param tree el árbol de medicamentos.
	 * @return si tiene el medicamento o no.
	 */
	public static boolean contains_medication(String name, BinaryTree<ClinicCase> tree){
		return contains_medication(name, tree.root());
	}
	
	/**
	 * Método auxiliar para verificar si hay ya existe un medicamento.
	 * @param name el nombre del medicamento.
	 * @param node el nodo inicial.
	 * @return si contiene o no.
	 */
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
	
	/**
	 * @param tree el arbol de medicamentos.
	 * @return los medicamentos de una cita especifica.
	 */
	public static String get_appointment_medication(BinaryTree<ClinicCase> tree){
		return get_appointment_medication(tree.root(), "");
	}
	
	/**
	 * Método auxiliar para encontrar la medicacion de una cita.
	 * @param node el nodo inicial.
	 * @param result donde se arma la lista.
	 * @return la lista de medicamentos.
	 */
	private static String get_appointment_medication(TreeNode<ClinicCase> node, String result) {
		if (node == null) {
			return "";
		}
		result += get_appointment_medication(node.get_left(), result);
		result += get_appointment_medication(node.get_right(),result);
		result += node.data().get_medication_list();
		return result;
	}
	
	/**
	 * @param tree el árbol de exámenes.
	 * @return la lista de exámenes para una cita especifica.
	 */
	public static String get_appointment_tests(BinaryTree<ClinicCase> tree){
		return get_appointment_tests(tree.root(), "");
	}
	
	/**
	 * Método auxiliar para encontrar la lista de exámenes.
	 * @param node el nodo inicial.
	 * @param result donde se arma la lista.
	 * @return la lista en string.
	 */
	private static String get_appointment_tests(TreeNode<ClinicCase> node, String result) {
		if (node == null) {
			return "";
		}
		result += get_appointment_tests(node.get_left(), result);
		result += get_appointment_tests(node.get_right(),result);
		result += node.data().get_tests_list();
		return result;
	}
	
	
}
