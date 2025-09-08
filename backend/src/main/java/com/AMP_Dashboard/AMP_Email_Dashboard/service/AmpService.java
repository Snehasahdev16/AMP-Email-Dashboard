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
                "Convert the following HTML into valid AMP4EMAIL. Return ONLY AMP4EMAIL code no extra fences or any questions :" +
                        "no !important, no external fonts, no animations" +
                        "Use the below attached boilerplate as starting point\n" +
                        "\n" +
                        "Remove any text (Twig) outside the <body> tag and copy it just below the<body>\n" +
                        "\n" +
                        "Replace “mso-table-lspace: 0pt; mso-table-rspace: 0pt;“ with empty string.\n" +
                        "\n" +
                        "Replace “<img“ with “<amp-img height=10“. This might introduce a duplicate height attribute error. All the height attributes should be checked via inspection of normal HTML after it renders in browser.\n" +
                        "\n" +
                        "Remove the style attributes that are throwing error in AMP Playground. Some of them are:\n" +
                        "\n" +
                        "    -moz-border-radius\n" +
                        "\n" +
                        "    -webkit-border-radius\n" +
                        "\n" +
                        "AMP only allows links with HTTPS protocol, make sure that all the placeholders or constant links evaluate to such. At last, we can ignore the errors which are due to placeholders where there is supposed to be a HTTPS link.\n" +
                        "\n" +
                        "Change ubaOpenRateTrackingUrl to ubaOpenRateTrackingUrlAmp , This <amp-img> should be of width and height 1px, instead of 0 as in HTML, this will affect the Open Tracking of AMP email. AMP requires both height and width as attribute so that the size of the image can be known beofre even rendering the image, is the width, height is 0 for image, it was observered that the Open Tracking Pixel was not triggered.\n" +
                        "\n" +
                        "If yours is use case involving forms, do checkout the sample code for all kinds of forms: \n" +
                        "\n" +
                        "AMP Playground \n" +
                        "\n" +
                        "We can use AMP state to store the status of form submission as follows, add this in<form> tag if you want to show different banners after success-submission and error-submission. These variables can be used to hide or show any element like: <td>, <div> etc.\n" +
                        "on=\"submit-success:AMP.setState({ form_response : event.response, form_success : 'show', form_submitted : 'show'});submit-error:AMP.setState({form_submitted : 'show', form_error: 'show'})\">\n" +
                        "\n" +
                        "    These states can be used to compute class attribute of an element: For eg: If the form is submitted with success, then form_success would have been set to show above and will display the below thanks element.\n" +
                        "\n" +
                        "<tr id=\"thanks\" class=\"hide\" [class]=\"form_success ? 'show' : 'hide'\">\n" +
                        "\n" +
                        "Same goes for error element:\n" +
                        "\n" +
                        "        <tr id=\"error\" class=\"hide\" [class]=\"form_error ? 'show' : 'hide'\">\n" +
                        "\n" +
                        "Boilerplate AMP Code:\n" +
                        "<!doctype html>\n" +
                        "<html amp4email lang=\"en\" data-css-strict>\n" +
                        "\n" +
                        "<head>\n" +
                        "   <meta charset=\"utf-8\">\n" +
                        "   <style amp4email-boilerplate>\n" +
                        "      body {\n" +
                        "         visibility: hidden\n" +
                        "      }\n" +
                        "   </style>\n" +
                        "   <script async src=\"https://cdn.ampproject.org/v0.js\"></script>\n" +
                        "   <script async custom-element=\"amp-form\" src=\"https://cdn.ampproject.org/v0/amp-form-0.1.js\"></script>\n" +
                        "   <script async custom-element=\"amp-bind\" src=\"https://cdn.ampproject.org/v0/amp-bind-0.1.js\"></script>\n" +
                        "\n" +
                        "   <style amp-custom>\n" +
                        "      form.amp-form-submit-success>input {\n" +
                        "         display: none\n" +
                        "      }\n" +
                        "\n" +
                        "      .show {}\n" +
                        "\n" +
                        "      .hide {\n" +
                        "         display: none;\n" +
                        "      }\n" +
                        "   </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "</body>\n" +
                        "</html>" +
                        "\n" +
                        htmlInput;
        return callGemini(prompt);
    }

    public String convertFromIdea(String idea) {
        String prompt =
                "Generate a complete AMP4EMAIL HTML email from this idea: \"" + idea + "\". " +
                        "Start with <!doctype html> and <html ⚡4email>. Follow all AMP4EMAIL rules.Return ONLY AMP4EMAIL code no extra fences or any questions : +\n" +
                        "no !important, no external fonts, no animations" +
                        "Use the below attached boilerplate as starting point\n" +
                        "\n" +
                        "Remove any text (Twig) outside the <body> tag and copy it just below the<body>\n" +
                        "\n" +
                        "Replace “mso-table-lspace: 0pt; mso-table-rspace: 0pt;“ with empty string.\n" +
                        "\n" +
                        "Replace “<img“ with “<amp-img height=10“. This might introduce a duplicate height attribute error. All the height attributes should be checked via inspection of normal HTML after it renders in browser.\n" +
                        "\n" +
                        "Remove the style attributes that are throwing error in AMP Playground. Some of them are:\n" +
                        "\n" +
                        "    -moz-border-radius\n" +
                        "\n" +
                        "    -webkit-border-radius\n" +
                        "\n" +
                        "AMP only allows links with HTTPS protocol, make sure that all the placeholders or constant links evaluate to such. At last, we can ignore the errors which are due to placeholders where there is supposed to be a HTTPS link.\n" +
                        "\n" +
                        "Change ubaOpenRateTrackingUrl to ubaOpenRateTrackingUrlAmp , This <amp-img> should be of width and height 1px, instead of 0 as in HTML, this will affect the Open Tracking of AMP email. AMP requires both height and width as attribute so that the size of the image can be known beofre even rendering the image, is the width, height is 0 for image, it was observered that the Open Tracking Pixel was not triggered.\n" +
                        "\n" +
                        "If yours is use case involving forms, do checkout the sample code for all kinds of forms: \n" +
                        "\n" +
                        "AMP Playground \n" +
                        "\n" +
                        "We can use AMP state to store the status of form submission as follows, add this in<form> tag if you want to show different banners after success-submission and error-submission. These variables can be used to hide or show any element like: <td>, <div> etc.\n" +
                        "on=\"submit-success:AMP.setState({ form_response : event.response, form_success : 'show', form_submitted : 'show'});submit-error:AMP.setState({form_submitted : 'show', form_error: 'show'})\">\n" +
                        "\n" +
                        "    These states can be used to compute class attribute of an element: For eg: If the form is submitted with success, then form_success would have been set to show above and will display the below thanks element.\n" +
                        "\n" +
                        "<tr id=\"thanks\" class=\"hide\" [class]=\"form_success ? 'show' : 'hide'\">\n" +
                        "\n" +
                        "Same goes for error element:\n" +
                        "\n" +
                        "        <tr id=\"error\" class=\"hide\" [class]=\"form_error ? 'show' : 'hide'\">\n" +
                        "\n" +
                        "Boilerplate AMP Code:\n" +
                        "<!doctype html>\n" +
                        "<html amp4email lang=\"en\" data-css-strict>\n" +
                        "\n" +
                        "<head>\n" +
                        "   <meta charset=\"utf-8\">\n" +
                        "   <style amp4email-boilerplate>\n" +
                        "      body {\n" +
                        "         visibility: hidden\n" +
                        "      }\n" +
                        "   </style>\n" +
                        "   <script async src=\"https://cdn.ampproject.org/v0.js\"></script>\n" +
                        "   <script async custom-element=\"amp-form\" src=\"https://cdn.ampproject.org/v0/amp-form-0.1.js\"></script>\n" +
                        "   <script async custom-element=\"amp-bind\" src=\"https://cdn.ampproject.org/v0/amp-bind-0.1.js\"></script>\n" +
                        "\n" +
                        "   <style amp-custom>\n" +
                        "      form.amp-form-submit-success>input {\n" +
                        "         display: none\n" +
                        "      }\n" +
                        "\n" +
                        "      .show {}\n" +
                        "\n" +
                        "      .hide {\n" +
                        "         display: none;\n" +
                        "      }\n" +
                        "   </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "</body>\n" +
                        "</html>";
        return callGemini(prompt);
    }

    public String convertIdeaToForm(String idea) {
        String prompt =
                "You’re an expert in AMP for Email. Based on the following idea, generate a valid AMP4EMAIL email FORM.\n\n" +
                        "Idea:\n" + idea + "\n\n" +
                        "Requirements:\n" +
                        "1. Must start with <!doctype html> and <html amp4email lang=\"en\" data-css-strict>.\n" +
                        "2. Include required AMP runtime script and amp-form script:\n" +
                        "   <script async src=\"https://cdn.ampproject.org/v0.js\"></script>\n" +
                        "   <script async custom-element=\"amp-form\" src=\"https://cdn.ampproject.org/v0/amp-form-0.1.js\"></script>\n" +
                        "3. The <form> must have method=\"post\", action-xhr=\"https://example.com/submit\", and target=\"_top\".\n" +
                        "4. Only allow valid AMP input types: text, email, textarea, select, option, input type=submit.\n" +
                        "   Disallowed: input type=button, image, file.\n" +
                        "5. Add proper <label> for each input and use required where appropriate.\n" +
                        "6. Include success and error handling:\n" +
                        "   <div submit-success><template type=\"amp-mustache\">Success message</template></div>\n" +
                        "   <div submit-error><template type=\"amp-mustache\">Error message</template></div>\n" +
                        "7. Keep CSS within <style amp-custom>, no !important, no animations.\n" +
                        "8. Return ONLY the valid AMP4EMAIL HTML code, no explanations or markdown fences.\n";

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


