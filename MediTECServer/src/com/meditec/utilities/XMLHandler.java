package com.meditec.utilities;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.meditec.datastructures.AVLTree;
import com.meditec.datastructures.BinaryTree;
import com.meditec.datastructures.SplayTree;
import com.meditec.medmanagement.ClinicCase;
import com.meditec.medmanagement.MedicTest;
import com.meditec.medmanagement.Medication;

public class XMLHandler {
	
	public static Medication find_medication(String name){
		try{
			File medication_file = new File("C:/Users/dell-pc/Desktop/MediTEC Server git/MediTECServer/xmlfiles/medication.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(medication_file);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("medication");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (element.getElementsByTagName("name").item(0).getTextContent().toLowerCase().equals(name)) {
						return new Medication((element.getElementsByTagName("name").item(0).getTextContent()) ,  element.getElementsByTagName("price").item(0).getTextContent(),  element.getAttribute("id"));
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static MedicTest find_test(String name){
		try{
			File tests_file = new File("C:/Users/dell-pc/Desktop/MediTEC Server git/MediTECServer/xmlfiles/tests.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(tests_file);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("test");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (element.getElementsByTagName("name").item(0).getTextContent().toLowerCase().equals(name)) {
						return new MedicTest((element.getElementsByTagName("name").item(0).getTextContent()) ,  element.getElementsByTagName("price").item(0).getTextContent(),  element.getAttribute("id"));
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void add_cases_to_tree(BinaryTree<ClinicCase> cases_tree){
		try{
			File cases_file = new File("C:/Users/dell-pc/Desktop/MediTEC Server git/MediTECServer/xmlfiles/cases.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(cases_file);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("case");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;
					cases_tree.insert(Integer.parseInt(element.getAttribute("id")),
							new ClinicCase((element.getElementsByTagName("name").item(0).getTextContent()) ,   
							element.getAttribute("id"),
							element.getElementsByTagName("medication").item(0).getTextContent(),
							element.getElementsByTagName("tests").item(0).getTextContent()));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void add_tests_to_tree(SplayTree<MedicTest> tests_tree){
		try{
			File cases_file = new File("C:/Users/dell-pc/Desktop/MediTEC Server git/MediTECServer/xmlfiles/tests.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(cases_file);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("test");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;
					tests_tree.insert(new MedicTest(element.getElementsByTagName("name").item(0).getTextContent(), element.getElementsByTagName("price").item(0).getTextContent() , element.getAttribute("id")), Integer.parseInt(element.getAttribute("id")));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void add_medication_to_tree(AVLTree<Medication> medication_tree){
		try{
			File cases_file = new File("C:/Users/dell-pc/Desktop/MediTEC Server git/MediTECServer/xmlfiles/medication.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(cases_file);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("medication");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;
					medication_tree.insert(new Medication(element.getElementsByTagName("name").item(0).getTextContent(), element.getElementsByTagName("price").item(0).getTextContent() , element.getAttribute("id")));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
