package com.example.hackathon_summer.domain.dto.child.request;

import com.example.hackathon_summer.domain.entity.Child;
import com.example.hackathon_summer.domain.entity.File;
import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.enums.BloodType;
import com.example.hackathon_summer.enums.Sex;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class ChildProfileDto {
    @NotNull
    private String name;

    @NotNull
    private int age;

    @NotNull
    private Sex sex;

    @NotNull
    private String birth;

    @NotNull
    private BloodType bloodType;

    @NotNull
    private Double height;

    @NotNull
    private Double weight;

    private String introduce = null;

    @NotNull
    private MultipartFile file;

    @NotNull
    private String agencyUrl;

    public Child toEntity(File childImg, User user) {
        return Child.builder()
                .name(name)
                .age(age)
                .sex(sex)
                .birth(birth)
                .bloodType(bloodType)
                .height(height)
                .weight(weight)
                .introduce(introduce)
//                .file(childImg)
                .agencyUrl(agencyUrl)
                .user(user)
                .build();
    }
}
