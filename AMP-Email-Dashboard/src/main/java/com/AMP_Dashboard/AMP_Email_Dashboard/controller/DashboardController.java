package com.AMP_Dashboard.AMP_Email_Dashboard.controller;


import com.AMP_Dashboard.AMP_Email_Dashboard.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private EmailService emailService;


    @GetMapping("/health")
    public String healthCheck(){
        return "Dashboard API is running";
    }
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendAmpEmail(emailRequest.getRecipient(), emailRequest.getAmpHtmlCode());
            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }
}



    // DTO for request body
    class EmailRequest {
        private String ampHtmlCode;
        private static String recipient;

        public String getAmpHtmlCode() {
            return ampHtmlCode;
        }
        public void setAmpHtmlCode(String ampHtmlCode) {
            this.ampHtmlCode = ampHtmlCode;
        }

        public static String getRecipient() {
            return recipient;
        }
        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }

}
