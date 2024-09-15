package com.assignment.message.controller;

import com.assignment.message.entity.Message;
import com.assignment.message.dao.MessageRequest;
import com.assignment.message.dao.MessageResponse;
import com.assignment.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest messageRequest, @PathVariable String token,@PathVariable String userName) throws Exception {

        Message savedMessage = messageService.saveMessage(messageRequest.getMessage(),messageRequest.getEncryptionType(), token, userName);
        return ResponseEntity.ok(new MessageResponse(savedMessage.getEncryptedMessage(), savedMessage.toString()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getMessage(@PathVariable Long id,@PathVariable String token,@PathVariable String userName) throws Exception {
        Optional<Message> message = messageService.getMessageById(id, token, userName);
        if (message.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MessageResponse(message.get().getEncryptedMessage(), id.toString()));
    }
}
