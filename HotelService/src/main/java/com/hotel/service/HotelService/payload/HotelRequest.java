package com.hotel.service.HotelService.payload;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class HotelRequest {
    private String hotelName;
    private String hotelLocation;
    private String hotelAbout;
}