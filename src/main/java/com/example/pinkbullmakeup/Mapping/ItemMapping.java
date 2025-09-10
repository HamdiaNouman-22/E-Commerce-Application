package com.example.pinkbullmakeup.Mapping;

import com.example.pinkbullmakeup.DTO.ItemToAddInCart;
import com.example.pinkbullmakeup.Entity.Cart;
import com.example.pinkbullmakeup.Entity.CartItem;
import com.example.pinkbullmakeup.Entity.Product;
import com.example.pinkbullmakeup.Service.CartService;
import com.example.pinkbullmakeup.Service.ProductService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ItemMapping {

    @Mapping(target = "productInCart", expression = "java(mapProduct(item.getProductId(), productService))")
    @Mapping(target = "productQuantity", source = "productQuantity")
    @Mapping(target = "cart", ignore = true) // You set it manually in service
    @Mapping(target = "priceAccordingToQuantity", ignore = true) // Still calculated
    CartItem toCartItem(ItemToAddInCart item, @Context ProductService productService);

    List<CartItem> toCartItemList(List<ItemToAddInCart> items, @Context ProductService productService);

    default Product mapProduct(String productId, @Context ProductService productService) {
        return productService.findById(UUID.fromString(productId));
    }
}

