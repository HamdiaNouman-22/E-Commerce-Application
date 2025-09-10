package com.example.pinkbullmakeup.Repository;

import com.example.pinkbullmakeup.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
