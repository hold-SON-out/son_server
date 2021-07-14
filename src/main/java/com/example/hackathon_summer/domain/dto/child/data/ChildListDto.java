package com.example.hackathon_summer.domain.dto.child.data;

import com.example.hackathon_summer.domain.dto.file.FileDto;
import com.example.hackathon_summer.domain.entity.Child;
import com.example.hackathon_summer.domain.entity.File;
import com.example.hackathon_summer.enums.Sex;
import lombok.Data;

@Data
public class ChildListDto {
    private Long idx;

    private String name;

    private int age;

    private String area;

    private Sex sex;

    private FileDto file;

    public ChildListDto(Child child, String area, FileDto file) {
        this.idx = child.getIdx();
        this.name = child.getName();
        this.age = child.getAge();
        this.area = area;
        this.sex = child.getSex();
        this.file = file;
    }
}
