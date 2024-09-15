package com.assignment.message.dao;

public class MessageResponse {
    private String encryptedMessage;
    private String messageId;

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public void setEncryptedMessage(String encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }

    public String getMessageId() {
        return messageId;
    }


    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public MessageResponse(String encryptedMessage, String messageId) {
        this.encryptedMessage = encryptedMessage;
        this.messageId = messageId;
    }
}
