package com.hotel.service.HotelService.service;

import com.hotel.service.HotelService.entity.Hotel;

import java.util.List;

public interface HotelService{
    Hotel saveHotel(Hotel hotel);
    List<Hotel> getAllHotel();
    Hotel getHotelByHotelId(Integer hotelId);
}
