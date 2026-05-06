package com.rahul.cartify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rahul.cartify.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
