package com.example.pinkbullmakeup.DTO;

import com.example.pinkbullmakeup.Entity.Shade;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponseForCustomer {
    private String productId;
    private String prodName;
    private String prodImage;
    private String prodCategory;
    private String prodBrand;
    private float prodPrice;
    private List<Shade> shades;
}
