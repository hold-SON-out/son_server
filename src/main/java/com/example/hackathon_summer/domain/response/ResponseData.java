package com.example.hackathon_summer.domain.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseData<T> extends Response{
    private T data;
}
