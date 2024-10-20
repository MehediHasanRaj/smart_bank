package com.raj.smart_bank.service.impl;

import com.raj.smart_bank.dto.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendEmail;

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(emailDetails.getSubject());
            message.setTo(emailDetails.getRecipient());
            message.setText(emailDetails.getEmailBody());
            message.setFrom(sendEmail);

            javaMailSender.send(message);
            System.out.println("mail sent successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
