package com.meditec.utilities;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
						System.out.println("Found " + element.getElementsByTagName("name").item(0).getTextContent());
						return new Medication((element.getElementsByTagName("name").item(0).getTextContent()) ,  element.getElementsByTagName("price").item(0).getTextContent(),  element.getAttribute("id"));
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
