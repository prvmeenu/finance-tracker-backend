package com.example.FinanceTracker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegiesterRequest {

    @NotBlank(message = "Name is required")
    private String username;
    @Size(min = 6, message = "Password must be at least 6 Character ")
    private String password;
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;
}
