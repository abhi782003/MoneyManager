package in.abhijeet.moneymanager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String brevoApiKey;

    private final String BREVO_API_URL = "https://api.brevo.com/v3/smtp/email";

    public void sendEmail(String toEmail, String subject, String body) {
        RestTemplate restTemplate = new RestTemplate();

        // Request body
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, String> sender = new HashMap<>();
        sender.put("name", "Money Manager");
        sender.put("email", "tapsaleabhijeet@gmail.com"); // verified sender in Brevo
        requestBody.put("sender", sender);

        Map<String, String> to = new HashMap<>();
        to.put("email", toEmail);
        requestBody.put("to", new Map[]{to});

        requestBody.put("subject", subject);
        requestBody.put("htmlContent", body);

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", brevoApiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(BREVO_API_URL, request, String.class);
            System.out.println("Brevo response: " + response.getStatusCode() + " - " + response.getBody());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
