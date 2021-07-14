package com.example.hackathon_summer.domain.repository;

import com.example.hackathon_summer.domain.entity.Child;
import com.example.hackathon_summer.enums.Sex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChildRepo extends JpaRepository<Child, Long> {
    @Query(value = "SELECT * FROM Child ORDER BY RAND()", nativeQuery = true)
    List<Child> findAllRandom();
    List<Child> findAllByNameContaining(String name);
    List<Child> findAllByAge(int age);
    List<Child> findAllBySex(Sex sex);
    @Query(value = "SELECT * FROM Child WHERE user_id = (SELECT idx FROM User WHERE area = ?)",
    nativeQuery = true)
    List<Child> findAllByArea(String area);
}
