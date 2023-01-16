package com.example.secondteamproject.jwt;

import com.example.secondteamproject.exception.SecurityExceptionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(JwtUtil.AUTHORIZATION_HEADER); // Authorization의 키로 오는 Bearer Token  == 토큰으로 나를 증명한다 (약속)
        String refreshToken = request.getHeader(JwtUtil.REFRESH_AUTHORIZATION_HEADER);

        //AccessToken 인증,인가 체크
        if (accessToken != null) {
            if (accessToken.startsWith(JwtUtil.BEARER_PREFIX)) {
                String resolvedAccessToken = jwtUtil.resolveAccessToken(accessToken);
                validCheckAndGetUserinfoAndSetAuthentication(resolvedAccessToken, response);
            }
        }
        //RefreshToken 인증,인가 체크
        if (refreshToken != null) {
            if (refreshToken.startsWith(JwtUtil.REFRESH_PREFIX)) {
                String resolvedFreshToken = jwtUtil.resolveRefreshToken(refreshToken);
                validCheckAndGetUserinfoAndSetAuthentication(resolvedFreshToken, response);
            }
        }
        filterChain.doFilter(request, response);
    }
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    public void validCheckAndGetUserinfoAndSetAuthentication(String Token, HttpServletResponse response) {
        if (!jwtUtil.validateToken(Token)) {
            jwtExceptionHandler(response, "Access Token Error", HttpStatus.UNAUTHORIZED.value());
            return;
        }
        Claims info = jwtUtil.getUserInfoFromToken(Token);
        setAuthentication(info.getSubject());
    }

}