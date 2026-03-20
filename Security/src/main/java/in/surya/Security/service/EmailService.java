package in.surya.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    public void sendWelcomeEmail(String toEmail, String name) {
        // Implement email sending logic using mailSender
        // You can create a SimpleMailMessage or MimeMessage based on your needs
        // Set the fromEmail as the sender's address
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Welcome to our application");
        message.setText("Dear "+name+",\n\nThank you for registering with our application. We are excited to have you on board!\n\nBest regards,\nThe Team");
        mailSender.send(message);
    }
    public void sendResetOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Password Reset OTP");
        message.setText("Dear User,\n\nYour OTP for password reset is: " + otp + ". This OTP is valid for 15 minutes.\n\nBest regards,\nThe Team");
        mailSender.send(message);
    }
    public void sendVerificationOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Acount verification otp");
        message.setText("Your otp is "+otp+" verify your account with this otp");
        mailSender.send(message);
    }
}