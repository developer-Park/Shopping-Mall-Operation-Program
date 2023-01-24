package com.example.secondteamproject.service;

import com.example.secondteamproject.dto.token.TokenResponseDto;
import com.example.secondteamproject.dto.user.SigninRequestDto;
import com.example.secondteamproject.dto.user.SignupRequestDto;
import com.example.secondteamproject.entity.Admin;
import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.entity.UserRoleEnum;
import com.example.secondteamproject.dto.user.LogOutRequestDTO;

import javax.servlet.http.HttpServletResponse;

public interface GeneralService {
    TokenResponseDto reissue(String username, UserRoleEnum role);
    boolean deleteUser(Long id, User user);
    //redis
    void logout(LogOutRequestDTO logout);

    boolean deleteUserByAdmin(Long id, Admin admin);

    public void adminsignup(SignupRequestDto signupRequestDto);

    public void userSignUp(SignupRequestDto signupRequestDto);

    Seller findBySellername(String name);
    User findByUsername(String name);
    Admin findByAdminname(String name);

    TokenResponseDto userSignIn(SigninRequestDto signinRequestDto, HttpServletResponse response);

    TokenResponseDto adminSignIn(SigninRequestDto signinRequestDto);

    TokenResponseDto sellerSignIn(SigninRequestDto signinRequestDto);
    TokenResponseDto removeDuplicated(String accessToken,String refreshToken1);
}
