package com.example.pinkbullmakeup.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddReview {
    @NotBlank
    private String customerId;
    @NotBlank
    private String productId;
    @NotBlank
    private String reviewMessage;
    @NotNull
    private int rating;
}
