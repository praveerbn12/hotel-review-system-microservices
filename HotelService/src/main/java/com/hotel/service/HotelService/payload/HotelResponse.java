package com.hotel.service.HotelService.payload;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class HotelResponse {
    private Integer hotelId;
    private String hotelName;
    private String hotelLocation;
    private String hotelAbout;
}
