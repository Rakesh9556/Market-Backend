package org.rakeshg.retailstore.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank private String name;
    @NotBlank private String phone;
    private String email;
    @NotBlank private String role;



}
