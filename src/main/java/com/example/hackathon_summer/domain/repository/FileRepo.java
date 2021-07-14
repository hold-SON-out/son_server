package com.example.hackathon_summer.domain.repository;

import com.example.hackathon_summer.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<File, Long> {
}
