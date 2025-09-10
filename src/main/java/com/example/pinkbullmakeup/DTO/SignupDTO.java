package com.example.pinkbullmakeup.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupDTO {
    @NotBlank
    @Size(max = 25, message = "Size exceeded")
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(max = 16, message = "Size exceeded")
    private String password;
    @NotBlank
    private String phone;
    @NotBlank
    @Size(max = 50, message = "Size exceeded")
    private String address;
    @NotBlank
    private String postalCode;
    @NotBlank
    private String city;
}
