package com.example.pinkbullmakeup.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemToAddInCart {
    @NotBlank
    private String productId;
    @NotBlank
    private String shadeName;
    @NotNull
    @Max(100)
    @Min(1)
    private int productQuantity;
}
