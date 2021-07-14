package com.example.hackathon_summer.service;

import com.example.hackathon_summer.domain.dto.auth.request.LoginDto;
import com.example.hackathon_summer.domain.dto.auth.request.UserRegisterDto;
import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.domain.repository.UserRepo;
import com.example.hackathon_summer.enums.JwtAuth;
import com.example.hackathon_summer.enums.Purpose;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
    private final UserRepo userRepository;

    @Value("${auth.access}")
    private String ACCESS_SECRET_KEY;
    @Value("${auth.refresh}")
    private String REFRESH_SECRET_KEY;

    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Transactional
    public void register(UserRegisterDto userRegisterDto) {
        try {
            if(userRegisterDto.getPurpose() == Purpose.ADOPTED) {
                userRepository.save(userRegisterDto.toEntity(Purpose.ADOPTED));
            } else {
                userRepository.save(userRegisterDto.toEntity(Purpose.ADOPTING));
            }

        } catch(HttpClientErrorException e) {
            throw e;
        } catch(Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    @Transactional(readOnly = true)
    public String isThereId(String id) {
        if (userRepository.findUserById(id).isEmpty()) {
            return "사용 가능한 아이디입니다.";
        } else {
            return "사용 불가능한 아이디입니다.";
        }
    }

    public Long login(LoginDto loginDto) {
        try {
            User user = userRepository.findUserByIdAndPassword(loginDto.getId(), loginDto.getPassword()).orElseThrow(
                    () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "없는 회원입니다.")
            );

            return user.getIdx();
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    public String createToken(Long idx, JwtAuth authType) {
        Date expiredAt = new Date();
        byte[] key = null;

        if(authType == JwtAuth.ACCESS) {
            expiredAt = new Date(expiredAt.getTime() + 1000 * 60 * 60);
            key = DatatypeConverter.parseBase64Binary(ACCESS_SECRET_KEY);
        } else if (authType == JwtAuth.REFRESH) {
            expiredAt = new Date(expiredAt.getTime() + 1000 * 60 * 60 * 24 * 7);
            key = DatatypeConverter.parseBase64Binary(REFRESH_SECRET_KEY);
        }

        Key signInKey  = new SecretKeySpec(key, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(idx.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredAt.getTime()))
                .signWith(signInKey, signatureAlgorithm)
                .compact();
    }

    @Transactional(readOnly = true)
    public User accessTokenDecoding(String token) {
        try {
            Claims claims = decodingToken(token, ACCESS_SECRET_KEY);
            Long idx = Long.valueOf(claims.getSubject()).longValue();
            return userRepository.findById(idx).orElseGet(() -> {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "존재하지 않는 유저");
            });

        } catch (Exception e) {
            throw e;
        }
    }

    private Claims decodingToken(String token, String key) {
        log.info(token);
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.GONE, "토큰 만료");
        } catch (SignatureException | MalformedJwtException e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조");
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }

    public String refreshTokenDecoding(String token) {
        try {
            Claims claims = decodingToken(token, REFRESH_SECRET_KEY);
            return claims.getSubject();
        } catch (Exception e) {
            throw e;
        }
    }
}
