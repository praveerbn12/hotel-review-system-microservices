package com.rating.service.RatingService.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RatingRequest {
    @NotNull(message = "userId is required")
    private Integer userId;
    @NotNull(message = "hotelId is required")
    private Integer hotelId;
    @NotNull @Min(value = 1, message = "rating must be 1-5") @Max(value = 5, message = "rating must be 1-5")
    private Integer ratingInStar;
    @NotBlank(message = "remark is required")
    private String remark;
}