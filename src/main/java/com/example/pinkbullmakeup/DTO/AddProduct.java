package com.example.pinkbullmakeup.DTO;

import com.example.pinkbullmakeup.Entity.Shade;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.List;

@Data
public class AddProduct {
    @NotBlank
    @Size(max = 25)
    private String prodName;
    @NotBlank
    private String prodImage;
    private String prodCategory;
    private String prodBrand;
    @NotNull
    @Min(0)
    private float prodPrice;
    @NotNull
    @Min(10)
    private int quantityInStock;
    private List<Shade> shades;
}
