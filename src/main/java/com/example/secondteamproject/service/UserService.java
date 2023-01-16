package com.example.secondteamproject.service;

import com.example.secondteamproject.dto.token.TokenResponseDto;
import com.example.secondteamproject.dto.user.SigninRequestDto;
import com.example.secondteamproject.dto.user.SignupRequestDto;
import com.example.secondteamproject.entity.Admin;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.entity.UserRoleEnum;

public interface UserService {
    void signup(SignupRequestDto signupRequestDto);
    TokenResponseDto signin(SigninRequestDto signinRequestDto);
    TokenResponseDto reissue(String username, UserRoleEnum role);
    boolean deleteUser(Long id, User user);
    User findByUsername(String name);
    Admin findByAdminname(String name);
}
