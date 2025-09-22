package com.AMP_Dashboard.AMP_Email_Dashboard.controller;

/**
 * Data Transfer Object for the incoming request.
 * It now cleanly accepts both html and idea, which can be blank.
 */
public class GenerateRequest {
    private String html;
    private String idea;

    // Getters
    public String getHtml() {
        return html;
    }

    public String getIdea() {
        return idea;
    }

    // Setters
    public void setHtml(String html) {
        this.html = html;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }
}



