package com.SCM.SCM20.Services.Impl;

import com.SCM.SCM20.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.properties.domain_name}")
    private String domain_name;

    @Override
    public void sendEmail(String to, String subject, String body) {


        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom(domain_name);

            javaMailSender.send(message);
        }catch (Exception e){
            System.out.println("error in email sending");
            e.printStackTrace();
        }

    }







    @Override
    public void sendEmailWithHtml() {

    }

    @Override
    public void sendEmailWithAttachment() {

    }
}
