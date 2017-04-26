package com.meditec.clientmanagement;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class QRManager {
	
	private final String SENDER = "mss97@hotmail.es";
	
	public void send_qr(String receiver, String name){
		
		EmailAttachment attachment = new EmailAttachment();
		
		attachment.setPath("xmlfiles/unblocked.png");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("QRCode");
		attachment.setName("MediTECAuthQRCode");
		
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("localhost");
		try {
			email.addTo(receiver,name);
			email.setFrom(SENDER, "MediTEC Services");
			email.setMsg("Here's your Unblocking QR Code");
			
			email.attach(attachment);
			
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}	
	}
}
