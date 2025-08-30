package com.AMP_Dashboard.AMP_Email_Dashboard;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAmpEmail(String recipient, String ampHtml) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        // helper for header fields
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(recipient);
        helper.setSubject("AMP Builder with AI - Email from Dashboard");
        helper.setFrom("yourgmail@gmail.com"); // replace with your Gmail

        // Fallback HTML
        String fallbackHtml = "<html><body><h2>AMP Email Fallback</h2>"
                + "<p>Your client does not support AMP. Here’s a preview:</p>"
                + "<pre>" + ampHtml + "</pre></body></html>";

        // Build multipart/alternative
        MimeMultipart multipart = new MimeMultipart("alternative");

        // HTML Part
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(fallbackHtml, "text/html; charset=UTF-8");
        multipart.addBodyPart(htmlPart);

        // AMP Part
        MimeBodyPart ampPart = new MimeBodyPart();
        ampPart.setContent(ampHtml, "text/x-amp-html; charset=UTF-8");
        multipart.addBodyPart(ampPart);

        // Attach multipart to message
        message.setContent(multipart);

        mailSender.send(message);
        System.out.println("✅ AMP Email sent successfully to " + recipient);
    }
}
