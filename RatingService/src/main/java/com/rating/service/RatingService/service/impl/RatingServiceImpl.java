package com.rating.service.RatingService.service.impl;

import com.rating.service.RatingService.entity.Rating;
import com.rating.service.RatingService.payload.RatingRequest;
import com.rating.service.RatingService.payload.RatingResponse;
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
    public RatingResponse saveRating(RatingRequest req) {
        Rating rating = new Rating();
        rating.setUserId(req.getUserId());
        rating.setHotelId(req.getHotelId());
        rating.setRatingInStar(req.getRatingInStar());
        rating.setRemark(req.getRemark());
        return toResponse(ratingRepository.save(rating));
    }


    @Override
    public List<RatingResponse> getAllRating() {
        return ratingRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<RatingResponse> getAllRatingByUserId(Integer userId) {
        return ratingRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    @Override
    public List<RatingResponse> getAllRatingByHotelId(Integer hotelId) {
        return ratingRepository.findByHotelId(hotelId).stream().map(this::toResponse).toList();
    }

    private RatingResponse toResponse(Rating rating) {
        RatingResponse res = new RatingResponse();
        res.setRatingId(rating.getRatingId());
        res.setUserId(rating.getUserId());
        res.setHotelId(rating.getHotelId());
        res.setRatingInStar(rating.getRatingInStar());
        res.setRemark(rating.getRemark());
        return res;
    }
}
