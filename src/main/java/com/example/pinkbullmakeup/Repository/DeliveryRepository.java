package com.example.pinkbullmakeup.Repository;

import com.example.pinkbullmakeup.Entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
