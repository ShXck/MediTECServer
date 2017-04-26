package com.meditec.utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.FileSystemNotFoundException;

import com.meditec.clientmanagement.Patient;
import com.meditec.datastructures.List;
import com.meditec.medmanagement.Medic;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLHandler {
	
	private static XStream xStream = new XStream(new DomDriver("UTF-8"));
	private static List<Patient> deserialized_patients = new List<>();
	
	public static void serialize_patient(Patient patient){
		
		try{
			FileOutputStream fs = new FileOutputStream("xmlfiles/Patients.xml");
			xStream.toXML(patient,fs);
		}catch (FileNotFoundException e) {
			e.getMessage();
		}
	}
	
	public static void serialize_medic(Medic medic){
		try{
			FileOutputStream fs = new FileOutputStream("xmlfiles/Medics.xml");
			xStream.toXML(medic,fs);
		}catch (FileNotFoundException e) {
			e.getMessage();
		}
	}
}
