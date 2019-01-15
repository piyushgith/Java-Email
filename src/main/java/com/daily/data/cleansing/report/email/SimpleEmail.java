package com.daily.data.cleansing.report.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmail {

	@Autowired
    private JavaMailSender sender;
	
	public String sendMail(File file) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper =null;
        
        try {
        	helper=new MimeMessageHelper(message,true);
            helper.setTo("demo@gmail.com");
            helper.setText("Greetings :)\n Please find the attached docuemnt for your reference.");
            helper.setSubject("Mail From Spring Boot");
           // ClassPathResource attachment = new ClassPathResource(file.getAbsolutePath());
            //helper.addAttachment(file.getName(), attachment);
           
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }
	
	
}
