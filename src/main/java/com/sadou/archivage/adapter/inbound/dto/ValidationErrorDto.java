package com.sadou.archivage.adapter.inbound.dto;

import java.util.List;
import java.util.Map;

public class ValidationErrorDto {
    private String message;
    private Map<String, List<String>> fieldErrors;
    private String timestamp;

    public ValidationErrorDto() {}

    public ValidationErrorDto(String message, Map<String, List<String>> fieldErrors) {
        this.message = message;
        this.fieldErrors = fieldErrors;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    // Getters et Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, List<String>> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
} 