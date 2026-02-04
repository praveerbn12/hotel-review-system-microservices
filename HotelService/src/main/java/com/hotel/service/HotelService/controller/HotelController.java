package com.hotel.service.HotelService.controller;

import com.hotel.service.HotelService.entity.Hotel;
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
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        Hotel hotel1=hotelService.saveHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel1);
    }

    @GetMapping("/getHotel")
    public ResponseEntity<List<Hotel>> getAllHotel(){
        List<Hotel>  hotelList=hotelService.getAllHotel();
        return  ResponseEntity.status(HttpStatus.OK).body(hotelList);
    }

    @GetMapping("/getHotel/{hotelId}")
    public ResponseEntity<Hotel> getUserById(@PathVariable Integer hotelId ){
       Hotel hotel=hotelService.getHotelByHotelId(hotelId);
        return  ResponseEntity.status(HttpStatus.OK).body(hotel);
    }


}
