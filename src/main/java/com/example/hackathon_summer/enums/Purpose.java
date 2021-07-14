package com.example.hackathon_summer.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Purpose {
    ADOPTING("구매자", "ADOPTING"),
    ADOPTED("기관", "ADOPTED");

    private final String title;
    private final String key;
}
