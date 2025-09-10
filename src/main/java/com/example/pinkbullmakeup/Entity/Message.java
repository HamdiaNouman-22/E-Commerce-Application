package com.example.pinkbullmakeup.Entity;

import com.example.pinkbullmakeup.ENUMS.MessageStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Message {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID messageId;

    @NotBlank
    @Size(max=25)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 50)
    private String message;

    @NotNull
    private LocalDateTime receivedAt;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    public Message() {}

    public Message(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.messageStatus = MessageStatus.UNCHECKED;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }
}
