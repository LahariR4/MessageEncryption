package com.assignment.message.service;
import com.assignment.message.dao.EncryptionRequest;
import com.assignment.message.dao.EncryptionResponse;
import com.assignment.message.dao.Role;
import com.assignment.message.entity.Message;
import com.assignment.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(String originalMessage, String encryptionType,String token, String userName)  throws  IOException,Exception{

        // Process the response
         boolean isValid = this.validateTokenAndRole(token, userName, Role.MESSAGE_WRITER.toString());
        if(isValid){
            RestTemplate restTemplate = new RestTemplate();
            String encrypUrl = "http://localhost:8082/api/encrypt";

            // Create the request body
            EncryptionRequest requestBody = new EncryptionRequest();
            requestBody.setMessage(originalMessage);
            requestBody.setEncryptionType(encryptionType);

            // Create headers if needed
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json"); // Set content type to JSON

            // Create HttpEntity with body and headers
            HttpEntity<EncryptionRequest> requestEntity = new HttpEntity<>(requestBody, headers);

            // Make the POST request
            ResponseEntity<EncryptionResponse> response = restTemplate.exchange(encrypUrl, HttpMethod.POST, requestEntity, EncryptionResponse.class);

            // Process the response
            EncryptionResponse encryptionBody = response.getBody();
            if(encryptionBody.getEncryptedMessage() != null){
                Message message = new Message(1,originalMessage, encryptionBody.getEncryptedMessage(), encryptionType);
                return messageRepository.save(message);
            }else{
                throw new IOException("System failed while creating encrypted message");
            }
        }
        else{
            throw new Exception("Token/ Role validation failed");
        }

    }

    public Optional<Message> getMessageById(Long id, String token, String userName) throws  Exception {
        boolean isValid = this.validateTokenAndRole(token, userName, Role.MESSAGE_READER.toString());
        if(isValid){
            return messageRepository.findById(id);
        }else{
            throw new Exception("Token/ Role validation failed");
        }

    }

    public boolean messageExists(Long id) {
        return messageRepository.existsById(id);
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public boolean validateTokenAndRole(String token, String userName, String role) {
        RestTemplate restTemplate = new RestTemplate();
        // Create a Map for the path variables
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("token", token);
        uriVariables.put("userName", userName);
        uriVariables.put("role", role);
        String authUrl = "http://localhost:8083/api/auth/token/verify-authorise";

        //Client can call this api directly, for time being adding in this call
        ResponseEntity<Boolean> tokenAndRoleValidateResponse = restTemplate.exchange(authUrl, HttpMethod.GET, null,Boolean.class,uriVariables);

        // Process the response
        return Boolean.TRUE.equals(tokenAndRoleValidateResponse.getBody());
    }
}