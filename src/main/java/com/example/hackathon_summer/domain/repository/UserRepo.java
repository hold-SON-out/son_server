package com.example.hackathon_summer.domain.repository;

import com.example.hackathon_summer.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByIdAndPassword(String id, String password);
    Optional<User> findUserById(String id);
//    @Query("SELECT COUNT (*) FROM User WHERE id = ?")
    int countById(String id);
    Optional<User> findUserByNameContaining(String name);
}
