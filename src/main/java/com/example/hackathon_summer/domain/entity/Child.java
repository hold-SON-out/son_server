package com.example.hackathon_summer.domain.entity;

import com.example.hackathon_summer.enums.BloodType;
import com.example.hackathon_summer.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private Sex sex;

    @Column
    private String birth;

    @Column
    private BloodType bloodType;

    @Column
    private Double height;

    @Column
    private Double weight;

    @Column
    private String introduce;

    @OneToOne(mappedBy = "child", cascade = CascadeType.REMOVE)
    private ChildFileCon users;

    @Column(columnDefinition = "TEXT")
    private String agencyUrl;
}
