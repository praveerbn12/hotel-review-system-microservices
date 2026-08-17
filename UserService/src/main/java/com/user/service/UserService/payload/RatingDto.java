package com.user.service.UserService.payload;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private Integer ratingId;
    @NotNull private Integer userId;
    @NotNull private Integer hotelId;
    @NotNull
    @Min(1) @Max(5) private Integer ratingInStar;
    @NotBlank
    private String remark;
}
