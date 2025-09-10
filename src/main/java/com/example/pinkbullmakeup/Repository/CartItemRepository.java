package com.example.pinkbullmakeup.Repository;

import com.example.pinkbullmakeup.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> findByCart_CartIdAndProductInCart_ProductId(UUID cartId, UUID productId);

    boolean existsByCart_CartIdAndProductInCart_ProductId(UUID cartId, UUID productId);

}
