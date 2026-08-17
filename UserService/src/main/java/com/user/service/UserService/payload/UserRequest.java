package com.user.service.UserService.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "userName is required")
    private String userName;

    @NotBlank(message = "userEmail is required")
    @Email(message = "userEmail must be a valid email")
    private String userEmail;

    private String userAbout;
}