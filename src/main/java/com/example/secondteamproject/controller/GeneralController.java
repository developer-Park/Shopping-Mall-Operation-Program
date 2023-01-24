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
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/general")
public class GeneralController {

    private final GeneralService generalService;
    private final JwtUtil jwtUtil;



    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }
    @GetMapping("/signup/admin")
    public ModelAndView signupAdminPage() {
        return new ModelAndView("signupAdmin");
    }


    @GetMapping("/login-page")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }


    @PostMapping(path = "/user-signup", consumes = "application/x-www-form-urlencoded")
    public String userSignUp(@Validated SignupRequestDto signupRequestDto) {
        generalService.userSignUp(signupRequestDto);
        return "success";
    }

    @PostMapping(path = "/admin-signup", consumes = "application/x-www-form-urlencoded")
    public String adminSignUp(@Validated SignupRequestDto signupRequestDto) {
        generalService.adminsignup(signupRequestDto);
        return "success";
    }
    @ResponseBody
    @PostMapping(path = "/user-signin" ,consumes = "application/json")
    public String userSignIn(@RequestBody SigninRequestDto signinRequestDto, HttpServletResponse response) {
        System.out.println(signinRequestDto.getUsername());
        System.out.println(signinRequestDto.getPassword());
        generalService.userSignIn(signinRequestDto,response);
        return "success";
    }
    @ResponseBody
    @PostMapping("/admin-signin")
    public TokenResponseDto adminSignIn(@RequestBody SigninRequestDto signinRequestDto) {
        return generalService.adminSignIn(signinRequestDto);
    }

    @ResponseBody
    @PostMapping("/seller-signin")
    public TokenResponseDto sellerSignIn(@RequestBody SigninRequestDto signinRequestDto) {
        return generalService.sellerSignIn(signinRequestDto);
    }
    /**
     * Delete user
     * Writer by Park
     */
    @PreAuthorize("isAuthenticated() and (( #userDetails.username == principal.username ) or hasRole('ADMIN'))")
    @DeleteMapping("/{userId}")
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


