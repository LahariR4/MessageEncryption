package com.assignment.message.dao;

import lombok.AllArgsConstructor;

@AllArgsConstructor()
public class EncryptionResponse {

    private String encryptedMessage;

    public void setEncryptedMessage(String encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }
}
