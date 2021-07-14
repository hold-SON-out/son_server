package com.example.hackathon_summer.domain.dto.auth.request;

import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.enums.Purpose;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDto {
    @NotNull
    private String id;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private Purpose purpose;

    private String agency = null;

    private String area = null;

    public User toEntity(Purpose purpose) {
        return User.builder()
                .id(id)
                .password(password)
                .name(name)
                .purpose(purpose)
                .agency(agency)
                .area(area)
                .build();
    }
}
