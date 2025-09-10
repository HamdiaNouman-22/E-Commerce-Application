package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.DTO.MessageReceived;
import com.example.pinkbullmakeup.ENUMS.MessageStatus;
import com.example.pinkbullmakeup.Entity.Message;
import com.example.pinkbullmakeup.Exceptions.ResourceNotFoundException;
import com.example.pinkbullmakeup.Repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessageFromClient(MessageReceived messageReceived){
        Message message = new Message(messageReceived.getName(), messageReceived.getEmail(), messageReceived.getMessage());

        messageRepository.save(message);
    }

    public Message changeMessageStatusToChecked(UUID messageId){
        Message message = messageRepository.findById(messageId).orElseThrow(()-> new ResourceNotFoundException("Message not found."));

        message.setMessageStatus(MessageStatus.CHECKED);

        return messageRepository.save(message);
    }
}
