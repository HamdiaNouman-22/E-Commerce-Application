package com.example.pinkbullmakeup.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageReceived {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String message;
}
