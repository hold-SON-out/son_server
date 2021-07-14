package com.example.hackathon_summer.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ChildFileCon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne
    @JoinColumn(nullable = false, name = "child")
    private Child child;

    @OneToOne
    @JoinColumn(nullable = false, name = "file")
    private File file;

    public ChildFileCon(Child child, File file) {
        this.child = child;
        this.file = file;
    }
}
