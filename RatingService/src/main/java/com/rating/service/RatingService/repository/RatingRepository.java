package com.rating.service.RatingService.repository;

import com.rating.service.RatingService.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Integer> {
    List<Rating> findByUserId(Integer userId);
    List<Rating> findByHotelId(Integer hotelId);
}
