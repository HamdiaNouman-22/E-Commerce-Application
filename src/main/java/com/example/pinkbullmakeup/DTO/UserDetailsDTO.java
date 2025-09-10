package com.example.pinkbullmakeup.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDetailsDTO {
    private UUID id;
    private String name;
    private String email;
    private String role;
    private String phone;
    private String address;

    public UserDetailsDTO(UUID id, String name, String email, String role,String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.phone = phone;
        this.address = address;
    }

    public UserDetailsDTO(UUID id, String name, String email, String role) {
        this(id, name, email, role, null, null);
    }
}
