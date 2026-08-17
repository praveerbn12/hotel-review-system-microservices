package com.hotel.service.HotelService.service;

import com.hotel.service.HotelService.entity.Hotel;
import com.hotel.service.HotelService.payload.HotelRequest;
import com.hotel.service.HotelService.payload.HotelResponse;

import java.util.List;

public interface HotelService{
    HotelResponse saveHotel(HotelRequest hotelRequest);
    List<HotelResponse> getAllHotel();
    HotelResponse getHotelByHotelId(Integer hotelId);
}
