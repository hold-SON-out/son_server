package com.example.hackathon_summer.domain.dto.child.data;

import com.example.hackathon_summer.domain.dto.file.FileDto;
import com.example.hackathon_summer.domain.entity.Child;
import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.enums.BloodType;
import com.example.hackathon_summer.enums.Sex;
import lombok.Data;

@Data
public class ChildDetailDto {
    private Long idx;
    private String name;
    private int age;
    private Sex sex;
    private String birth;
    private BloodType bloodType;
    private Double height;
    private Double weight;
    private String introduce;
    private FileDto file;
    private String agency;
    private String area;
    private String agencyUrl;

    public ChildDetailDto(Child child, User author, FileDto file) {
        this.idx = child.getIdx();
        this.name = child.getName();
        this.age = child.getAge();
        this.sex = child.getSex();
        this.birth = child.getBirth();
        this.bloodType = child.getBloodType();
        this.height = child.getHeight();
        this.weight = child.getWeight();
        this.introduce = child.getIntroduce();
        this.file = file;
        this.agency = author.getAgency();
        this.area = author.getArea();
        this.agencyUrl = child.getAgencyUrl();
    }
}
