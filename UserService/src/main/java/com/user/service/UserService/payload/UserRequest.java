package com.user.service.UserService.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String userName;
    private String userEmail;
    private String userAbout;
}