package com.example.secondteamproject.jwt;

import com.example.secondteamproject.dto.token.AuthenticatedUser;
import com.example.secondteamproject.entity.UserRoleEnum;
import com.example.secondteamproject.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;


/**
 * Writer By Park
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailsServiceImpl userDetailsService;
    //토큰 생성에 필요한 값
    public static final String AUTHORIZATION_HEADER = "Authorization"; //Header KEY 값
    public static final String REFRESH_AUTHORIZATION_HEADER = "Refresh_authorization"; //Header KEY 값
    public static final String AUTHORIZATION_KEY = "auth";  // 사용자 권한 값의 KEY.
    public static final String BEARER_PREFIX = "Bearer "; //토큰 식별자.
    public static final String REFRESH_PREFIX = "Refres "; //토큰 식별자.
    private static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; //1 hour // 60min X 60sec X 1000ms
    private static final Long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L; // 14 day
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct //객체 생성 시 초기화
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }
    // header에서 토큰 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER); // Authorization의 키로 오는 Bearer Token  == 토큰으로 나를 증명한다 (약속)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Writer by Park
     * this method for Refresh token in UserService
     * @param bearerToken
     * @return token value without bearer
     */
    public String resolveAccessToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public String resolveRefreshToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(REFRESH_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    /**
     * Writer by Park
     * @param username
     * @return create refresh token
     */
    public String refreshToken(String username, UserRoleEnum role) {
            Date date = new Date();
            return REFRESH_PREFIX +
                    Jwts.builder()
                            .setSubject(username)
                            .claim(AUTHORIZATION_KEY, role)
                            .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                            .setIssuedAt(date)
                            .signWith(key, signatureAlgorithm)
                            .compact();
        }
    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty");
        }
        return false;
    }
    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰 유효성 검사 후 인증된 사용자 정보 가져오기
    public AuthenticatedUser validateTokenAndGetInfo(String token) {
        if (validateToken(token)) {
            Claims claims = getUserInfoFromToken(token);
            String username = claims.getSubject();
            UserRoleEnum role = UserRoleEnum.valueOf(claims.get("auth").toString());
            return new AuthenticatedUser(role, username);
        }else {
            throw new IllegalArgumentException("유효하지 않은 토큰!!");
        }
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * Writer by Park
     * @param token
     * @return Extract Authentication from token
     */
    public Authentication getAuthentication(String token) {
        // Jwt 에서 claims 추출
        Claims claims = getUserInfoFromToken(token);
        // 권한 정보가 없음
        if (claims.get(AUTHORIZATION_KEY) == null) {
            throw new IllegalStateException("no Authentication ");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }



    public String logoutRefreshToken(String username, UserRoleEnum role) {
        Date date = new Date();
        return REFRESH_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String logoutCreateToken(String username, UserRoleEnum role) {
        Date date = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

}
