package com.user.service.UserService.payload;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer userId;
    private String userName;
    private String userEmail;
    private String userAbout;
    private List<RatingDto> ratingDtoList;
}