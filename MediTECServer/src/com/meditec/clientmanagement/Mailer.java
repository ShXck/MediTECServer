package com.meditec.clientmanagement;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailer {
	
	private static final String SENDER = "some@gmail.com";
	private static final String PASSWORD = "******";
	private static final String HOST = "smtp.gmail.com";
	private static final String PORT = "587";
	
	/**
	 * Envia un codigo QR al correo del paciente.
	 * @param receiver el correo destino.
	 * @param name el nombre del paciente.
	 */
	public static void send_qr(String receiver, String name){
		
		final String SUBJECT = "Desbloquea tu aplicación";
		final String MESSAGE = "Bienvenido a MediTEC, por favor escanea el siguiente código QR para desbloquear la aplicación";
		final String ATTACHMENT = "C:/Users/dell-pc/Desktop/MediTEC Server git/MediTECServer/xmlfiles/unblocked.png";
		
		Properties properties = new Properties();
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getDefaultInstance(properties);
		Message msg = new MimeMessage(session);
		
		try {
			msg.setFrom(new InternetAddress(SENDER));
			InternetAddress address = new InternetAddress(receiver);
			msg.setRecipient(Message.RecipientType.TO, address);
			msg.setSubject(SUBJECT);
			
			MimeBodyPart msg_part = new MimeBodyPart();
			msg_part.setText(MESSAGE);
			
			MimeBodyPart attachPart = new MimeBodyPart();
			attachPart.attachFile(new File(ATTACHMENT));
			attachPart.setHeader("Content-Type", "text/plain; charset=\"us-ascii\"; name=\"unblock.png\"");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(attachPart);
			multipart.addBodyPart(msg_part);

			msg.setContent(multipart);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Transport transport = session.getTransport("smtp");
			
			transport.connect(HOST, SENDER, PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Envia una notificacion al médico sobre una nueva cita.
	 * @param receiver el correo destino.
	 */
	public static void send_appointment_email(String receiver){
		
		final String SUBJECT = "Nueva cita";
		final String MESSAGE = "Se ha reservado una nueva cita con usted. Para ver los detalles de la cita dirigase al apartado de agenda en su aplicacion";
		
		Properties properties = new Properties();
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getDefaultInstance(properties);
		Message msg = new MimeMessage(session);
		
		try {
			msg.setFrom(new InternetAddress(SENDER));
			InternetAddress address = new InternetAddress(receiver);
			msg.setRecipient(Message.RecipientType.TO, address);
			msg.setSubject(SUBJECT);
			
			MimeBodyPart msg_part = new MimeBodyPart();
			msg_part.setText(MESSAGE);
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(msg_part);

			msg.setContent(multipart);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		try {
			Transport transport = session.getTransport("smtp");
			
			transport.connect(HOST, SENDER, PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * Envia un correo al paciente con los detalles finales de la cita.
	 * @param receiver el correo destino.
	 * @param symptoms 
	 * @param treatment
	 * @param tests
	 * @param cases
	 * @param cost
	 */
	public static void send_appointment_info(String receiver,String symptoms, String treatment, String tests, String cases , int cost){
		
		final String SUBJECT = "Informacion de la cita";
		final String MESSAGE = "A continuacion se le provee de los detalles de la cita:" + "\n Sintomas: " + symptoms + "\n Medicacion necesaria: " + treatment + "\n Examenes medicos requeridos: " + tests + "\n Casos clinicos relacionados: " + cases + "\n Costo total: " + cost + "\n Esperamos que nuestro servicio haya sido satifactorio.";
		
		
		Properties properties = new Properties();
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getDefaultInstance(properties);
		Message msg = new MimeMessage(session);
		
		try {
			msg.setFrom(new InternetAddress(SENDER));
			InternetAddress address = new InternetAddress(receiver);
			msg.setRecipient(Message.RecipientType.TO, address);
			msg.setSubject(SUBJECT);
			
			MimeBodyPart msg_part = new MimeBodyPart();
			msg_part.setText(MESSAGE);
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(msg_part);

			msg.setContent(multipart);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		try {
			Transport transport = session.getTransport("smtp");
			
			transport.connect(HOST, SENDER, PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}			
	}
}
