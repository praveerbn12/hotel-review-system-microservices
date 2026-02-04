package com.user.service.UserService.repository;

import com.user.service.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
