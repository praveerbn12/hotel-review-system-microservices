package com.rating.service.RatingService.service.impl;

import com.rating.service.RatingService.entity.Rating;
import com.rating.service.RatingService.repository.RatingRepository;
import com.rating.service.RatingService.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingRepository ratingRepository;
    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getAllRatingByUserId(Integer userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getAllRatingByHotelId(Integer hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }
}
