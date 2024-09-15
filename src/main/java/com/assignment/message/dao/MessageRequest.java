package com.assignment.message.dao;

public class MessageRequest {
    private String message;
    private String encryptionType;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getMessage() {
        return message;
    }

    public String getEncryptionType() {
        return encryptionType;
    }
}
