package com.example.hackathon_summer.domain.dto.user.data;

import com.example.hackathon_summer.domain.entity.User;
import lombok.Data;

@Data
public class UserDto {
    private Long idx;
    private String name;

    public UserDto(User user) {
        this.idx = user.getIdx();
        this.name = user.getName();
    }
}
