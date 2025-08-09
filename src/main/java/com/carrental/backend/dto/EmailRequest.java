package com.carrental.backend.dto;

import java.util.Map;

public class EmailRequest {
    
    private String to;
    private String subject;
    private String templateName;
    private Map<String, Object> templateData;
    
    // Constructors
    public EmailRequest() {}
    
    public EmailRequest(String to, String subject, String templateName, Map<String, Object> templateData) {
        this.to = to;
        this.subject = subject;
        this.templateName = templateName;
        this.templateData = templateData;
    }
    
    // Getters and Setters
    public String getTo() {
        return to;
    }
    
    public void setTo(String to) {
        this.to = to;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getTemplateName() {
        return templateName;
    }
    
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    
    public Map<String, Object> getTemplateData() {
        return templateData;
    }
    
    public void setTemplateData(Map<String, Object> templateData) {
        this.templateData = templateData;
    }
    
    @Override
    public String toString() {
        return "EmailRequest{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", templateName='" + templateName + '\'' +
                ", templateData=" + templateData +
                '}';
    }
} 