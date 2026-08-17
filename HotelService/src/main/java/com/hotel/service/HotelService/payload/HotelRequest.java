package com.hotel.service.HotelService.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class HotelRequest {
    @NotBlank(message = "hotelName is required")
    private String hotelName;
    @NotBlank(message = "hotelLocation is required")
    private String hotelLocation;
    private String hotelAbout;   // optional
}