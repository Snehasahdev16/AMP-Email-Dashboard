package com.AMP_Dashboard.AMP_Email_Dashboard.controller;

import com.AMP_Dashboard.AMP_Email_Dashboard.service.AmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/amp")
public class AmpController {

    @Autowired
    private AmpService ampService;

    @PostMapping("/generate")
        public GenerateResponse generateAmpEmail(@RequestBody GenerateRequest request) {
            String ampHtml;

            if (request.getHtml() != null && !request.getHtml().isBlank()) {
                ampHtml = ampService.convertFromHtml(request.getHtml());
            } else if (request.getIdea() != null && !request.getIdea().isBlank()) {
                ampHtml = ampService.convertFromIdea(request.getIdea());
            } else if (request.getFormIdea() != null && !request.getFormIdea().isBlank()) {
                // This is the new logic block to handle form generation
                ampHtml = ampService.convertIdeaToForm(request.getFormIdea());
            } else {
                ampHtml = "ERROR: Please provide either HTML, an idea, or a form idea.";
            }

            GenerateResponse response = new GenerateResponse();
            response.setAmpHtml(ampHtml);
            return response;
        }
    }







