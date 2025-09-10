package com.example.pinkbullmakeup.Repository;

import com.example.pinkbullmakeup.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUser_UserId(UUID userId);
}
