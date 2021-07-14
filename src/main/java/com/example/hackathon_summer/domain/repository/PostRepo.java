package com.example.hackathon_summer.domain.repository;

import com.example.hackathon_summer.domain.entity.Post;
import com.example.hackathon_summer.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllByTitleContaining(String title);
    List<Post> findAllByUser(User user);
}
