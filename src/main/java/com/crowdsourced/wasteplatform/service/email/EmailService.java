package com.crowdsourced.wasteplatform.service.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendComplaintResolvedEmail(String toEmail,
                                           String complaintTitle,
                                           String resolutionNote) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Complaint Has Been Resolved");
        message.setText(
                "Hello,\n\n" +
                "Your complaint titled: " + complaintTitle + " has been resolved.\n\n" +
                "Resolution note:\n" + resolutionNote + "\n\n" +
                "Thank you for using our platform."
        );

        mailSender.send(message);
    }
}
