package com.pup.cea.facs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.pup.cea.facs.model.Ticket;

@Service
public class EmailService {
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendEmail(Ticket ticket) {
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(ticket.getEmail());
		mail.setFrom("pupfacs.mail@gmail.com");
		mail.setSubject("Notice of " + ticket.getRemark());
		mail.setText("Dear " + ticket.getFacultyname() + "," +
					 "\n     This is to inform you that based on the Checker's routing List/Records, you were " + ticket.getRemark() + " from your class." +
					 "\n" +
					 "\n     TIME: " + ticket.getTimestart() + " to " + ticket.getTimeend() +
					 "\n      DAY: " + ticket.getDay() +
					 "\n     DATE: " + ticket.getDate() +
					 "\n" +
					 "\n     In view thereof, please submit to the HRMD, any comments/observations relative thereto. If any, within three (3) days upon recieving this notification."); 
		
		javaMailSender.send(mail);
	}
}
