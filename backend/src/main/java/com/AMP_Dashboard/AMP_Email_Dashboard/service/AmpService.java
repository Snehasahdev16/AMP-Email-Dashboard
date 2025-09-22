package com.AMP_Dashboard.AMP_Email_Dashboard.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AmpService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-pro:generateContent?key=%s";

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Reusable method
    private String callGemini(String prompt) {
        try {
            String requestBody = "{\n" +
                    "  \"contents\": [\n" +
                    "    {\n" +
                    "      \"parts\": [\n" +
                    "        { \"text\": " + escapeJson(prompt) + " }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(GEMINI_API_URL, geminiApiKey);

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return extractAmpHtml(response.getBody());
            } else {
                return "ERROR: Gemini request failed - " + response.getStatusCode() + ": " + response.getBody();
            }
        } catch (Exception e) {
            return "ERROR: Exception during Gemini request - " + e.getMessage();
        }
    }

    public String convertFromHtml(String htmlInput) {
        String prompt =
                    "Convert the following HTML into valid AMP4EMAIL. " +
                            "Return ONLY AMP4EMAIL code, no extra fences or explanations.The generated code should be Optimized and should athere to the below mentioned rules and work according to the user's input " +

                            "=== Global Rules === " +
                            "Always include <style amp4email-boilerplate>. " +
                            "No !important, no external fonts, no animations. " +
                            "AMP links must use HTTPS (placeholders that evaluate to HTTPS are acceptable). " +
                            "If the HTML uses interactive frameworks (e.g. amp-bind), refactor imperative event handlers into declarative AMP.setState logic. " +
                            "Minimize repetition: consolidate CSS rules and streamline HTML without losing clarity. " +

                            "=== HTML Transformations === " +
                            "Remove any text outside <body> and move it inside. " +
                            "Replace 'mso-table-lspace:0pt; mso-table-rspace:0pt;' with empty string. " +
                            "Replace every <img with <amp-img height=\"10\" and ensure width+height attributes are present. " +
                            "Change ubaOpenRateTrackingUrl → ubaOpenRateTrackingUrlAmp and ensure tracking <amp-img width=1 height=1>. " +
                            "Remove unsupported CSS attributes (-moz-border-radius, -webkit-border-radius, etc). " +
                            "All links must be HTTPS. " +

                            "=== Forms (ONLY if input HTML already has a form) === " +
                            "If the HTML contains a form, then convert it to <form method=\"post\" action-xhr=\"https://...\"> with amp-form. " +
                            "Add AMP.setState bindings for submit-success and submit-error. " +

                            "=== Boilerplate to Always Use === " +
                            "<!doctype html><html amp4email lang=\"en\" data-css-strict><head>" +
                            "<meta charset=\"utf-8\">" +
                            "<style amp4email-boilerplate>body{visibility:hidden}</style>" +
                            "<script async src=\"https://cdn.ampproject.org/v0.js\"></script>" +
                            "<script async custom-element=\"amp-form\" src=\"https://cdn.ampproject.org/v0/amp-form-0.1.js\"></script>" +
                            "<script async custom-element=\"amp-bind\" src=\"https://cdn.ampproject.org/v0/amp-bind-0.1.js\"></script>" +
                            "<style amp-custom>.show{} .hide{display:none}</style>" +
                            htmlInput;
        return callGemini(prompt);
    }

    public String convertFromIdea(String idea) {
        String prompt =
                "Generate a complete AMP4EMAIL HTML email from this idea: \"" + idea + "\". " +
                        "Return ONLY AMP4EMAIL code, no extra fences or explanations.The generated code should be Optimized and should athere to the below mentioned rules and work according to the user's input " +

                        "=== Global Rules === " +
                        "Always include <style amp4email-boilerplate>. " +
                        "No !important, no external fonts, no animations. " +
                        "AMP links must use HTTPS (placeholders that evaluate to HTTPS are acceptable). " +
                        "If interactive logic is needed, use amp-bind declaratively (not imperative onClick). " +
                        "Minimize repetition: consolidate CSS. " +

                        "=== Forms (ONLY if idea explicitly mentions a form) === " +
                        "If the idea requires a form (e.g., signup, feedback), use <form method=\"post\" action-xhr=\"https://...\"> with amp-form. " +
                        "Add AMP.setState for submit-success and submit-error. " +

                        "=== Boilerplate to Always Use === " +
                        "<!doctype html><html amp4email lang=\"en\" data-css-strict><head>" +
                        "<meta charset=\"utf-8\">" +
                        "<style amp4email-boilerplate>body{visibility:hidden}</style>" +
                        "<script async src=\"https://cdn.ampproject.org/v0.js\"></script>" +
                        "<script async custom-element=\"amp-form\" src=\"https://cdn.ampproject.org/v0/amp-form-0.1.js\"></script>" +
                        "<script async custom-element=\"amp-bind\" src=\"https://cdn.ampproject.org/v0/amp-bind-0.1.js\"></script>" +
                        "<style amp-custom>.show{} .hide{display:none}</style>" +
                        "</head><body></body></html>";
        return callGemini(prompt);
    }
    public String convertFromHtmlAndIdea(String html, String idea) {
        String prompt =
                "Convert the following into valid AMP4EMAIL. " +
                        "Inputs include some HTML and an idea description. " +
                        "Return ONLY the final AMP4EMAIL code, no extra fences or explanations. The generated code should be Optimized and should athere to the below mentioned rules and work according to the user's input" +

                        "=== Global Rules === " +
                        "Always include <style amp4email-boilerplate>. " +
                        "No !important, no external fonts, no animations. " +
                        "AMP links must use HTTPS. " +
                        "If interactive logic is needed, use amp-bind declaratively. " +
                        "Consolidate CSS, avoid duplication. " +

                        "=== HTML Transformations === " +
                        "Clean and convert provided HTML according to AMP4EMAIL rules (amp-img, boilerplate, HTTPS links, no disallowed attributes). " +

                        "=== Idea Application === " +
                        "Enhance or restructure the HTML according to the idea provided. " +

                        "=== Forms (ONLY if HTML or idea explicitly mentions a form) === " +
                        "If a form exists in either HTML or idea, convert it properly with amp-form and AMP.setState. " +

                        "=== Boilerplate to Always Use === " +
                        "<!doctype html><html amp4email lang=\"en\" data-css-strict><head>" +
                        "<meta charset=\"utf-8\">" +
                        "<style amp4email-boilerplate>body{visibility:hidden}</style>" +
                        "<script async src=\"https://cdn.ampproject.org/v0.js\"></script>" +
                        "<script async custom-element=\"amp-form\" src=\"https://cdn.ampproject.org/v0/amp-form-0.1.js\"></script>" +
                        "<script async custom-element=\"amp-bind\" src=\"https://cdn.ampproject.org/v0/amp-bind-0.1.js\"></script>" +
                        "<style amp-custom>.show{} .hide{display:none}</style>" +
                        "</head><body></body></html> " +

                        "HTML Input: " + html + " " +
                        "Idea: " + idea;
        return callGemini(prompt);
    }

    private String escapeJson(String text) {
        return "\"" + text.replace("\"", "\\\"").replace("\n", "\\n") + "\"";
    }

    private String extractAmpHtml(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode textNode = root.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text");

            if (!textNode.isMissingNode()) {
                return textNode.asText().trim();
            }
            return "ERROR: AMP4EMAIL text not found in Gemini response.";
        } catch (Exception e) {
            return "ERROR: Failed to parse Gemini response JSON - " + e.getMessage();
        }
    }
}


