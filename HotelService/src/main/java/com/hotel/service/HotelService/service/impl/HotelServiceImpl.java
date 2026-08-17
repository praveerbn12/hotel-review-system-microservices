package com.hotel.service.HotelService.service.impl;

import com.hotel.service.HotelService.entity.Hotel;
import com.hotel.service.HotelService.exception.ResourceNotFound;
import com.hotel.service.HotelService.payload.HotelRequest;
import com.hotel.service.HotelService.payload.HotelResponse;
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
    public HotelResponse saveHotel(HotelRequest hotelRequest) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelRequest.getHotelName());
        hotel.setHotelLocation(hotelRequest.getHotelLocation());
        hotel.setHotelAbout(hotelRequest.getHotelAbout());
        return toResponse(hotelRepository.save(hotel));
    }

    @Override
    public List<HotelResponse> getAllHotel() {
        return hotelRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public HotelResponse getHotelByHotelId(Integer hotelId) {
        Hotel hotel= hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFound("Hotel with id " + hotelId + " not found"));
        return toResponse(hotel);
    }

    private HotelResponse toResponse(Hotel hotel) {
        HotelResponse res = new HotelResponse();
        res.setHotelId(hotel.getHotelId());
        res.setHotelName(hotel.getHotelName());
        res.setHotelLocation(hotel.getHotelLocation());
        res.setHotelAbout(hotel.getHotelAbout());
        return res;
    }
}
