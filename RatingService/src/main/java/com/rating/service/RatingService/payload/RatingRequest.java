package com.rating.service.RatingService.payload;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RatingRequest {
    private Integer userId;
    private Integer hotelId;
    private Integer ratingInStar;
    private String remark;
}