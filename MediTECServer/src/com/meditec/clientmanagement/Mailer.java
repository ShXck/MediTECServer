package com.meditec.clientmanagement;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
	
	private final String SENDER = "correo@correo.com";
	private final String PASSWORD = "******";
	
	public void send_qr(String receiver, String name){
		
		Properties properties = System.getProperties();
		
		properties.put("mail.transport.protocol", "smtp");
		
		properties.setProperty("mail.smtp.host", "localhost");
		
		Session session = Session.getDefaultInstance(properties);
		
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER));
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(receiver));
			message.setSubject("MediTEC Unblocking QR Code");
			message.setText("Scan the code to unblock the app");
			
			Transport.send(message);
		}catch (MessagingException e) {
			e.printStackTrace();
		}
	
	}
}
