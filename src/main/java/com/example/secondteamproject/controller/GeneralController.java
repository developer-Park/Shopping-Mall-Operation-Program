package com.example.secondteamproject.controller;

import com.example.secondteamproject.dto.token.TokenRequestDto;
import com.example.secondteamproject.dto.token.TokenResponseDto;
import com.example.secondteamproject.dto.user.LogOutRequestDTO;
import com.example.secondteamproject.dto.user.ResolvedTokenAndAuthenticationDTO;
import com.example.secondteamproject.dto.user.SigninRequestDto;
import com.example.secondteamproject.dto.user.SignupRequestDto;
import com.example.secondteamproject.entity.Admin;
import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.jwt.JwtUtil;
import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.service.GeneralService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/general")
@Api(tags = "0. account")
public class GeneralController {

    private final GeneralService generalService;
    private final JwtUtil jwtUtil;

    @PostMapping("/user-signup")
    @ApiOperation(value = "고객 가입", notes = "고객 가입")
    @ApiImplicitParam(
        name = "signupRequestDto",
        value = "회원가입 신청",
        required = true,
        dataType = "SignupRequestDto",
        defaultValue = "None"
    )
    public String userSignUp(@Validated @RequestBody SignupRequestDto signupRequestDto) {
        generalService.userSignUp(signupRequestDto);
        return "success";
    }

    @PostMapping("/admin-signup")
    @ApiOperation(value = "관리자 가입", notes = "관리자 가입")
    public String adminSignUp(@Validated @RequestBody SignupRequestDto signupRequestDto) {
        generalService.adminsignup(signupRequestDto);
        return "success";
    }
    @ResponseBody
    @PostMapping("/user-signin")
    @ApiOperation(value = "고객 로그인", notes = "로그인 후 사용자 정보를 반환")
    public TokenResponseDto userSignIn(@RequestBody SigninRequestDto signinRequestDto) {
        return generalService.userSignIn(signinRequestDto);
    }
    @ResponseBody
    @PostMapping("/admin-signin")
    @ApiOperation(value = "관리자 로그인", notes = "로그인 후 사용자 정보를 반환")
    public TokenResponseDto adminSignIn(@RequestBody SigninRequestDto signinRequestDto) {
        return generalService.adminSignIn(signinRequestDto);
    }

    @ResponseBody
    @PostMapping("/seller-signin")
    @ApiOperation(value = "판매자 로그인", notes = "로그인 후 사용자 정보를 반환")
    public TokenResponseDto sellerSignIn(@RequestBody SigninRequestDto signinRequestDto) {
        return generalService.sellerSignIn(signinRequestDto);
    }
    /**
     * Delete user
     * Writer by Park
     */
    @PreAuthorize("isAuthenticated() and (( #userDetails.username == principal.username ) or hasRole('ADMIN'))")
    @DeleteMapping("/{userId}")
    @ApiOperation(value = "계정 삭제", notes = "특정 사용자 계정을 삭제 (관리자 전용)")
    public String deleteUser(@PathVariable Long userId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails.getUser() == null) {
            if (generalService.deleteUserByAdmin(userId, userDetails.getAdmin())) {
                return "Success delete user by admin";
            }
        } else {
            generalService.deleteUser(userId, userDetails.getUser());
            return "Success delete user";
        }
        throw new IllegalArgumentException("FAILED DELETE USER");
    }

    @PostMapping("/user-reissue")
    @ApiOperation(value = "고객 토큰 재발급", notes = "refresh token 을 활용하여 고객 토큰을 재발급")
    public TokenResponseDto userReissue(HttpServletRequest request, @RequestBody TokenRequestDto tokenRequestDto) {
        ResolvedTokenAndAuthenticationDTO resolvedTokenAndAuthenticationDTO =resolvedTokenAndAuthentication(request,tokenRequestDto);
        User refreshUser = generalService.findByUsername(resolvedTokenAndAuthenticationDTO.getAuthenticationFreshToken().getName());
        User accessUser = generalService.findByUsername(resolvedTokenAndAuthenticationDTO.getAuthenticationAccessToken().getName());
        if (accessUser == refreshUser) {
            return generalService.reissue(refreshUser.getUsername(), refreshUser.getRole());
        }
        throw new IllegalStateException("Vaild Error");
    }

    @PostMapping("/seller-reissue")
    @ApiOperation(value = "판매자 토큰 재발급", notes = "refresh token 을 활용하여 판매자 토큰을 재발급")
    public TokenResponseDto sellerReissue(HttpServletRequest request, @RequestBody TokenRequestDto tokenRequestDto) {
        ResolvedTokenAndAuthenticationDTO resolvedTokenAndAuthenticationDTO =resolvedTokenAndAuthentication(request,tokenRequestDto);
        Seller refreshSeller = generalService.findBySellername(resolvedTokenAndAuthenticationDTO.getAuthenticationFreshToken().getName());
        Seller accessSeller = generalService.findBySellername(resolvedTokenAndAuthenticationDTO.getAuthenticationAccessToken().getName());
        if (accessSeller == refreshSeller) {
            return generalService.reissue(refreshSeller.getSellerName(), refreshSeller.getRole());
        }
        throw new IllegalStateException("Vaild Error");
    }

    @PostMapping("/admin-reissue")
    @ApiOperation(value = "관리자 토큰 재발급", notes = "refresh token 을 활용하여 관리자 토큰을 재발급")
    public TokenResponseDto adminReissue(HttpServletRequest request, @RequestBody TokenRequestDto tokenRequestDto) {
        ResolvedTokenAndAuthenticationDTO resolvedTokenAndAuthenticationDTO =resolvedTokenAndAuthentication(request,tokenRequestDto);
        Admin refreshAdmin = generalService.findByAdminname(resolvedTokenAndAuthenticationDTO.getAuthenticationFreshToken().getName());
        Admin accessAdmin = generalService.findByAdminname(resolvedTokenAndAuthenticationDTO.getAuthenticationAccessToken().getName());
        if (accessAdmin == refreshAdmin) {
            return generalService.reissue(refreshAdmin.getAdminName(), refreshAdmin.getRole());
        }
        throw new IllegalStateException("Vaild Error");
    }


    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "redis 로그아웃")
    public String logout(@Validated @RequestBody LogOutRequestDTO logout) {
        generalService.logout(logout);
        // validation check
        return "Success Logout";
    }


    public ResolvedTokenAndAuthenticationDTO resolvedTokenAndAuthentication(HttpServletRequest request, @RequestBody TokenRequestDto tokenRequestDto) {
        String resolvedAccessToken = jwtUtil.resolveAccessToken(tokenRequestDto.getAccessToken());
        Authentication authenticationAccessToken = jwtUtil.getAuthentication(resolvedAccessToken);
        String refreshToken = request.getHeader(JwtUtil.REFRESH_AUTHORIZATION_HEADER);
        String resolvedRefreshToken = jwtUtil.resolveRefreshToken(refreshToken);
        Authentication authenticationFreshToken = jwtUtil.getAuthentication(resolvedRefreshToken);
        return new ResolvedTokenAndAuthenticationDTO(authenticationFreshToken,authenticationAccessToken);
    }
}


