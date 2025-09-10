package com.example.pinkbullmakeup.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ItemsSelected {
    private List<ItemToAddInCart> items;
}
