package com.user.service.UserService.payload;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "userName is required")
    private String userName;

    @NotBlank @Email(message = "valid email required")
    private String userEmail;

    @NotBlank(message = "password is required")
    private String password;

    private String userAbout;
}