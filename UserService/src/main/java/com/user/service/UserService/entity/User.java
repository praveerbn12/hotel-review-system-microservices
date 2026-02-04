package com.user.service.UserService.entity;

import com.user.service.UserService.payload.RatingDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String userName;
    private String userEmail;
    private String userAbout;
    @Transient
    private List<RatingDto> ratingDtoList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAbout() {
        return userAbout;
    }

    public void setUserAbout(String userAbout) {
        this.userAbout = userAbout;
    }

    public List<RatingDto> getRatingDtoList() {
        return ratingDtoList;
    }

    public void setRatingDtoList(List<RatingDto> ratingDtoList) {
        this.ratingDtoList = ratingDtoList;
    }
}
