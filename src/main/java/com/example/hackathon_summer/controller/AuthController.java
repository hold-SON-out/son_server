package com.example.hackathon_summer.controller;

import com.example.hackathon_summer.domain.dto.auth.request.LoginDto;
import com.example.hackathon_summer.domain.dto.auth.request.UserRegisterDto;
import com.example.hackathon_summer.domain.dto.auth.response.LoginResponse;
import com.example.hackathon_summer.domain.response.Response;
import com.example.hackathon_summer.domain.response.ResponseData;
import com.example.hackathon_summer.enums.JwtAuth;
import com.example.hackathon_summer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public Response register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        Response response = new Response();

        authService.register(userRegisterDto);

        response.setStatus(HttpStatus.OK);
        response.setMassage("회원가입 성공");

        return response;
    }

    @PostMapping("/login")
    public ResponseData<LoginResponse> login(@RequestBody @Valid LoginDto loginDto) {
        Long userIdx = authService.login(loginDto);
        String accessToken = authService.createToken(userIdx, JwtAuth.ACCESS);
        String refreshToken = authService.createToken(userIdx, JwtAuth.REFRESH);

        LoginResponse data = new LoginResponse(accessToken, refreshToken);

        ResponseData<LoginResponse> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("로그인 성공");
        responseData.setData(data);

        return responseData;
    }

    @PostMapping("/check")
    public ResponseData<String> checkId(@RequestParam String id) {
        ResponseData<String> responseData = new ResponseData<>();

        responseData.setStatus(HttpStatus.OK);
        responseData.setMassage("확인 성공");
        responseData.setData(authService.isThereId(id));

        return responseData;
    }
}
