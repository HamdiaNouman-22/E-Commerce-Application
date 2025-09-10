package com.example.pinkbullmakeup.Controller;

import com.example.pinkbullmakeup.Service.ChatbotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chatbot Controller", description = "API for interacting with the Gemini chatbot")
public class ChatBotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping
    @Operation(
            summary = "Get Chatbot Response",
            description = "Sends a prompt to the Gemini chatbot and receives a response.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response",
                            content = @Content(schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - missing or empty prompt",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "204", description = "No content - chatbot returned empty response",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<?> getChatResponse(@RequestBody Map<String, String> body) {
        try {
            String prompt = body.get("prompt");

            if (prompt == null || prompt.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Prompt is required.", "Missing prompt input."));
            }

            String reply = chatbotService.callGeminiApi(prompt);

            if (reply == null || reply.isBlank()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ErrorResponse("No reply from Gemini.", "Reply was empty or null."));
            }

            return ResponseEntity.ok(Map.of("reply", reply));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Failed to get response from chatbot.", e.getMessage()));
        }
    }

    // Simple error response class
    private static class ErrorResponse {
        private final String message;
        private final String error;

        public ErrorResponse(String message, String error) {
            this.message = message;
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public String getError() {
            return error;
        }
    }
}
