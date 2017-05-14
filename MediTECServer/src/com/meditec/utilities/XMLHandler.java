package com.meditec.utilities;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	
	public static void write_medication(String name, String cost, String id){
		try {
			
			String path = "C:/Users/dell-pc/Desktop/MediTEC Server git/MediTECServer/xmlfiles/medication.xml";
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(path);
			
			Node root = doc.getFirstChild();
			
			Element new_medication = doc.createElement("medication");
			root.appendChild(new_medication);

			new_medication.setAttribute("id", id);
			
			Element new_name = doc.createElement("name");
			new_name.appendChild(doc.createTextNode(name));
			new_medication.appendChild(new_name);
			
			
			Element new_cost = doc.createElement("price");
			new_cost.appendChild(doc.createTextNode(cost));
			new_medication.appendChild(new_cost);
			
			DOMSource source = new DOMSource(doc);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult result = new StreamResult(path);
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException x) {
			x.printStackTrace();
		} catch (TransformerException t) {
			t.printStackTrace();
		} catch (SAXException s) {
			s.printStackTrace();
		}
	}
	
	public static void write_test(String name, String cost, String id){
		try {
			
			String path = "C:/Users/dell-pc/Desktop/MediTEC Server git/MediTECServer/xmlfiles/tests.xml";
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(path);
			
			Node root = doc.getFirstChild();
			
			Element new_test = doc.createElement("test");
			root.appendChild(new_test);

			new_test.setAttribute("id", id);
			
			Element new_name = doc.createElement("name");
			new_name.appendChild(doc.createTextNode(name));
			new_test.appendChild(new_name);
			
			
			Element new_cost = doc.createElement("price");
			new_cost.appendChild(doc.createTextNode(cost));
			new_test.appendChild(new_cost);
			
			DOMSource source = new DOMSource(doc);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult result = new StreamResult(path);
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException x) {
			x.printStackTrace();
		} catch (TransformerException t) {
			t.printStackTrace();
		} catch (SAXException s) {
			s.printStackTrace();
		}
	}
	
	public static void write_case(String name, String treatment, String tests, String id){
		try {
			
			String path = "C:/Users/dell-pc/Desktop/MediTEC Server git/MediTECServer/xmlfiles/cases.xml";
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(path);
			
			Node root = doc.getFirstChild();
			
			Element new_case = doc.createElement("case");
			root.appendChild(new_case);

			new_case.setAttribute("id", id);
			
			Element new_name = doc.createElement("name");
			new_name.appendChild(doc.createTextNode(name));
			new_case.appendChild(new_name);
			
			
			Element medication = doc.createElement("medication");
			medication.appendChild(doc.createTextNode(treatment));
			new_case.appendChild(medication);
			
			Element new_tests = doc.createElement("tests");
			new_tests.appendChild(doc.createTextNode(tests));
			new_case.appendChild(new_tests);
			
			
			DOMSource source = new DOMSource(doc);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult result = new StreamResult(path);
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException x) {
			x.printStackTrace();
		} catch (TransformerException t) {
			t.printStackTrace();
		} catch (SAXException s) {
			s.printStackTrace();
		}
	}
}
