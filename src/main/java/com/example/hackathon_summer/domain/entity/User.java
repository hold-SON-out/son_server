package com.example.hackathon_summer.domain.entity;

import com.example.hackathon_summer.enums.Purpose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Purpose purpose;

    @Column
    private String agency;

    @Column
    private String area;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Child> children;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Comment> comments;
}
