package com.example.hackathon_summer.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String fileLocation;

    @Column
    private String fileType;

    @OneToOne(mappedBy = "file", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private ChildFileCon child;
}
