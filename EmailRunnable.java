package com.stetsiuk;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailRunnable implements Runnable {

	final private String text,
					subject,
					to;
	
	final String  d_email = "your_email",
	        d_uname = "user.name",
	        d_password = "your_password",
	        d_host = "localhost", // if you use local smtp server
	        d_port  = "25"; 
	
	Session session;
	
	Properties props = new Properties();
	
	EmailRunnable(String text, String subject, String to){
		this.text = text;
		this.subject = subject;
		this.to = to;
		
		props.put("mail.smtp.user", d_email);
		props.put("mail.smtp.host", d_host);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust","*");
	
		session = Session.getInstance(props);
		session.setDebug(true);
	}
	
	public void run() {
		Message msg = new MimeMessage(session);
		try {
			msg.setText(text);
		
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(d_email));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.saveChanges();
			
			Transport transport = session.getTransport("smtp");
			transport.connect(d_host, Integer.parseInt(d_port), d_uname, d_password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
