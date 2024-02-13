package com.sparta.duopleaseduo.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer";
    public final long TOKEN_TIME = 1000L * 60 * 30;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(String email) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public HttpServletResponse setTokenInCookie(String email, HttpServletResponse response) {
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, createToken(email));
        cookie.setMaxAge(1800);
        cookie.setPath("/");
        response.addCookie(cookie);
        return response;
    }

    public String substringToken(String tokenValue) {
        if(StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(6);
        }
        log.info("Not Found Token");
        throw new NullPointerException("Not found Token");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWt signature, 유효하지 않은 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) { // 쿠키 이름에 따라 수정
                    String token = cookie.getValue();
                    if (token != null && token.startsWith(BEARER_PREFIX)) {
                        return substringToken(token);
                    }
                }
            }
        }
        return null;
    }

    public String validateTokenAndGetUserName(HttpServletRequest request) {
        log.info("쿠키에 토큰존재여부 검사");
        String token = getTokenFromRequest(request);
        if(token != null) {
            log.info("토큰 유효성 검사");
            if(validateToken(token)){
                return getUserInfoFromToken(token).getSubject();
            };
        }
        throw new IllegalArgumentException("에러");
    }

    public HttpServletResponse expireToken(HttpServletResponse response) {
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER,"");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return response;
    }
}
