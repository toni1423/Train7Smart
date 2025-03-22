//package org.app.train7smartapp.user.service;
//
//import org.app.train7smartapp.user.model.User;
//import org.app.train7smartapp.web.dto.UserInformation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsService {
//
//    @Autowired
//    JavaMailSender javaMailSender;
//
//    public String sendEmail(User user, UserInformation userInformation) {
//        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setFrom(userInformation.getEmail());
//            mailMessage.setTo(user.getEmail());
//            mailMessage.setSubject("Welcome to my application!");
//            mailMessage.setText("Welcome to my application!");
//
//            javaMailSender.send(mailMessage);
//
//            return "Successfully!";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error!";
//        }
//    }
//}
