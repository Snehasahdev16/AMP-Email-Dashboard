package com.AMP_Dashboard.AMP_Email_Dashboard.controller;


public class GenerateRequest {
    private String html;
    private String idea;
    private String formIdea; // <-- ADDED THIS FIELD

    // Getters
    public String getHtml() { return html; }
    public String getIdea() { return idea; }
    public String getFormIdea() { return formIdea; } // <-- ADDED THIS GETTER

    // Setters
    public void setHtml(String html) { this.html = html; }
    public void setIdea(String idea) { this.idea = idea; }
    public void setFormIdea(String formIdea) { this.formIdea = formIdea; } // <-- ADDED THIS SETTER
}


