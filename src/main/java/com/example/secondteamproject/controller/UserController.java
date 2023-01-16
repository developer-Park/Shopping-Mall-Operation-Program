package com.example.secondteamproject.controller;

import com.example.secondteamproject.dto.token.TokenRequestDto;
import com.example.secondteamproject.dto.token.TokenResponseDto;
import com.example.secondteamproject.dto.user.SigninRequestDto;
import com.example.secondteamproject.dto.user.SignupRequestDto;
import com.example.secondteamproject.entity.Admin;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.jwt.JwtUtil;
import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public String signup(@Validated @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "success";
    }

    @ResponseBody
    @PostMapping("/signin")
    public TokenResponseDto signin(@RequestBody SigninRequestDto signinRequestDto) {
        return userService.signin(signinRequestDto);
    }


    /**
     * Delete user
     * Writer by Park
     */
    @PreAuthorize("isAuthenticated() and (( #userDetails.username == principal.username ) or hasRole('ADMIN'))")
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userService.deleteUser(userId, userDetails.getUser())) {
            return "Success delete user";
        } else {
            throw new IllegalArgumentException("Failed delete user");
        }
    }

    @PostMapping("/reissue")
    public TokenResponseDto reissue(HttpServletRequest request, @RequestBody TokenRequestDto tokenRequestDto) {
        String resolvedAccessToken = jwtUtil.resolveAccessToken(tokenRequestDto.getAccessToken());
        Authentication authenticationAccessToken = jwtUtil.getAuthentication(resolvedAccessToken);
        String refreshToken = request.getHeader(JwtUtil.REFRESH_AUTHORIZATION_HEADER);
        String resolvedRefreshToken = jwtUtil.resolveRefreshToken(refreshToken);
        Authentication authenticationFreshToken = jwtUtil.getAuthentication(resolvedRefreshToken);
        //Refrest 토큰 username가져오기
        User refreshUser = userService.findByUsername(authenticationFreshToken.getName());
        if (refreshUser == null) {
            //Access,refreshtoken 토큰 adminname가져오기
            Admin refreshAdmin = userService.findByAdminname(authenticationFreshToken.getName());
            Admin accessAdmin = userService.findByAdminname(authenticationAccessToken.getName());
            if (accessAdmin == refreshAdmin) {
                return userService.reissue(refreshAdmin.getAdminName(), refreshAdmin.getRole());
            }
        } else {
            //Access 토큰 username가져오기
            User accessUser = userService.findByUsername(authenticationAccessToken.getName());
            //두개 비교 후 맞으면 재발행
            if (accessUser == refreshUser) {
                return userService.reissue(refreshUser.getUsername(), refreshUser.getRole());
            }
        }
        throw new IllegalStateException("Vaild Error");
    }
}
