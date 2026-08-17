package com.rating.service.RatingService.payload;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RatingResponse {
    private Integer ratingId;
    private Integer userId;
    private Integer hotelId;
    private Integer ratingInStar;
    private String remark;
}