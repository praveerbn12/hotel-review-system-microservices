package com.hotel.service.HotelService.service.impl;

import com.hotel.service.HotelService.entity.Hotel;
import com.hotel.service.HotelService.repository.HotelRepository;
import com.hotel.service.HotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public Hotel saveHotel(Hotel hotel) {
        Hotel hotel1= hotelRepository.save(hotel);
        return hotel1;
    }

    @Override
    public List<Hotel> getAllHotel() {
        List<Hotel> hotelList= hotelRepository.findAll();
        return hotelList;
    }

    @Override
    public Hotel getHotelByHotelId(Integer hotelId) {
        Hotel hotel1= hotelRepository.findById(hotelId).orElseThrow(()-> new RuntimeException("Given Id is not found"));
        return hotel1;
    }
}
