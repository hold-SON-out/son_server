package com.example.hackathon_summer.domain.repository;

import com.example.hackathon_summer.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
