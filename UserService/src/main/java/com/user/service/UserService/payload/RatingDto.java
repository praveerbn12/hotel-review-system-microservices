package com.user.service.UserService.payload;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private Integer ratingId;
    private Integer userId;
    private Integer hotelId;
    private Integer ratingInStar;
    private  String  remark;
}
