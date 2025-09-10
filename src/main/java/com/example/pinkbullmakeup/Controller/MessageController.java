package com.example.pinkbullmakeup.Controller;

import com.example.pinkbullmakeup.DTO.MessageReceived;
import com.example.pinkbullmakeup.Entity.Message;
import com.example.pinkbullmakeup.Service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Message Controller", description = "APIs for managing client messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @Operation(
            summary = "Receive Message",
            description = "Receives a new message from a client and stores it in the system"
    )
    public ResponseEntity<Void> receiveMessage(@Valid @RequestBody MessageReceived messageReceived) {
        messageService.saveMessageFromClient(messageReceived);
        return ResponseEntity.ok().build(); // Or use created() if returning location
    }

    @PutMapping("/{id}/status")
    @Operation(
            summary = "Update Message Status",
            description = "Updates the status of a message to 'checked' by its ID"
    )
    public ResponseEntity<Message> updateMessageStatus(@PathVariable UUID id) {
        Message updatedMessage = messageService.changeMessageStatusToChecked(id);
        return ResponseEntity.ok(updatedMessage);
    }
}
