package com.hotel.service.HotelService.controller;

import com.hotel.service.HotelService.entity.Hotel;
import com.hotel.service.HotelService.payload.HotelRequest;
import com.hotel.service.HotelService.payload.HotelResponse;
import com.hotel.service.HotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    HotelService hotelService;
    @PostMapping("/addHotel")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody HotelRequest hotelRequest){
        HotelResponse hotel=hotelService.saveHotel(hotelRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel);
    }

    @GetMapping("/getHotel")
    public ResponseEntity<List<HotelResponse>> getAllHotel(){
        List<HotelResponse>  hotelList=hotelService.getAllHotel();
        return  ResponseEntity.status(HttpStatus.OK).body(hotelList);
    }

    @GetMapping("/getHotel/{hotelId}")
    public ResponseEntity<HotelResponse> getUserById(@PathVariable Integer hotelId ){
       HotelResponse hotel=hotelService.getHotelByHotelId(hotelId);
        return  ResponseEntity.status(HttpStatus.OK).body(hotel);
    }


}
