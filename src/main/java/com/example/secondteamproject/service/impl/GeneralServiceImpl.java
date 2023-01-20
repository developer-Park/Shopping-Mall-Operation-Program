package com.example.secondteamproject.service.impl;


import com.example.secondteamproject.dto.token.TokenResponseDto;
import com.example.secondteamproject.dto.user.SigninRequestDto;
import com.example.secondteamproject.dto.user.SignupRequestDto;
import com.example.secondteamproject.entity.Admin;
import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.entity.UserRoleEnum;
import com.example.secondteamproject.jwt.JwtUtil;
import com.example.secondteamproject.dto.user.LogOutRequestDTO;
import com.example.secondteamproject.repository.AdminRepository;
import com.example.secondteamproject.repository.SellerRepository;
import com.example.secondteamproject.repository.UserRepository;
import com.example.secondteamproject.service.GeneralService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GeneralServiceImpl implements GeneralService {

    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final AdminRepository adminRepository;
    private final RedisTemplate redisTemplate;
    @Transactional
    public void userSignUp(SignupRequestDto signupRequestDto) {
        String name = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        //회원 중복 확인
        User foundUser = userRepository.findByUsername(name);
        if (foundUser != null) {
            throw new IllegalArgumentException("Duplicated user");
        }
        Seller sellerFound = sellerRepository.findBySellerName(name);
        if (sellerFound != null) {
            throw new IllegalArgumentException("Duplicated Id");
        }
        Admin adminFound = adminRepository.findByAdminName(name);
        if (adminFound != null) {
            throw new IllegalArgumentException("Duplicated name");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(name, password, role, signupRequestDto.getImg(), signupRequestDto.getNickname(), signupRequestDto.getEmail());
        userRepository.save(user);

    }

    public void adminsignup(SignupRequestDto signupRequestDto) {
        String name = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        Admin admin = adminRepository.findByAdminName(name);
        if (admin != null) {
            throw new IllegalArgumentException("Duplicated admin");
        }
        //회원 중복 확인
        User foundUser = userRepository.findByUsername(name);
        if (foundUser != null) {
            throw new IllegalArgumentException("Duplicated user");
        }
        Seller sellerFound = sellerRepository.findBySellerName(name);
        if (sellerFound != null) {
            throw new IllegalArgumentException("Duplicated Id");
        }
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("Wrong admin password");
            }
            UserRoleEnum role = UserRoleEnum.ADMIN;
            Admin createAdmin = new Admin(name, password, role, signupRequestDto.getImg());
            adminRepository.save(createAdmin);
        }
    }

    /**
     * Writer by Park
     *
     * @param signinRequestDto
     * @return AccessToken, Refresh Token
     */
    @Transactional(readOnly = true)
    public TokenResponseDto userSignIn(SigninRequestDto signinRequestDto) {
        String username = signinRequestDto.getUsername();
        String password = signinRequestDto.getPassword();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Not found user");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        String accessToken = jwtUtil.createToken(user.getUsername(), user.getRole());
        String refreshToken1 = jwtUtil.refreshToken(user.getUsername(), user.getRole());
        TokenResponseDto tokenResponseDto = removeDuplicated(accessToken,refreshToken1);
        return tokenResponseDto;
    }

    @Transactional(readOnly = true)
    public TokenResponseDto adminSignIn(SigninRequestDto signinRequestDto) {
        String username = signinRequestDto.getUsername();
        String password = signinRequestDto.getPassword();
        Admin admin = adminRepository.findByAdminName(username);
        if (admin == null) {
            throw new IllegalArgumentException("Not found admin");
        }
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        String accessToken = jwtUtil.createToken(admin.getAdminName(), admin.getRole());
        String refreshToken1 = jwtUtil.refreshToken(admin.getAdminName(), admin.getRole());
        TokenResponseDto tokenResponseDto = removeDuplicated(accessToken,refreshToken1);
        return tokenResponseDto;
    }
    @Transactional(readOnly = true)
    public TokenResponseDto sellerSignIn(SigninRequestDto signinRequestDto) {
        String username = signinRequestDto.getUsername();
        String password = signinRequestDto.getPassword();
        Seller seller = sellerRepository.findBySellerName(username);
        if (seller == null) {
            throw new IllegalArgumentException("Not found seller");
        }
        if (!passwordEncoder.matches(password, seller.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        String accessToken = jwtUtil.createToken(seller.getSellerName(), seller.getRole());
        String refreshToken1 = jwtUtil.refreshToken(seller.getSellerName(), seller.getRole());
        TokenResponseDto tokenResponseDto = removeDuplicated(accessToken,refreshToken1);
        return tokenResponseDto;
    }


    /**
     * Writer by Park
     *
     * @param username, role
     * @return Reissue AccessToken, RefreshToken
     */
    @Transactional
    public TokenResponseDto reissue(String username, UserRoleEnum role) {
        String newCreatedToken = jwtUtil.createToken(username, role);
        String refreshToken1 = jwtUtil.refreshToken(username, role);
        //redis
        String resolvedFreshToken = jwtUtil.resolveRefreshToken(refreshToken1);
        Long tokenExpiration = jwtUtil.getExpiration(resolvedFreshToken);
        Authentication authentication = jwtUtil.getAuthentication(resolvedFreshToken);
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());
        if (ObjectUtils.isEmpty(refreshToken)) {
            throw new IllegalArgumentException("Logout account");
        }
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), resolvedFreshToken, tokenExpiration, TimeUnit.MILLISECONDS);
        //
        return new TokenResponseDto(newCreatedToken, refreshToken1);
    }

    @Transactional
    public boolean deleteUser(Long id, User user) {
        //포스트 존재 여부 확인
        User users = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Not found user"));
        if (users.isWriter(user.getUsername()) || UserRoleEnum.ADMIN == user.getRole()) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalArgumentException("Invalid user");
        }
    }

    @Transactional
    public boolean deleteUserByAdmin(Long id, Admin admin) {
        //포스트 존재 여부 확인
        User users = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Not found user"));
        if (UserRoleEnum.ADMIN == admin.getRole()) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalArgumentException("Invalid user");
        }
    }

    //redis
    @Transactional
    public void logout(LogOutRequestDTO logout) {
        String resolveAccessToken = jwtUtil.resolveAccessToken(logout.getAccessToken());
        if (!jwtUtil.validateToken(resolveAccessToken)) {
            throw new IllegalArgumentException("Wrong request");
        }
        Authentication authentication = jwtUtil.getAuthentication(resolveAccessToken);
        System.out.println(authentication.getName());
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }
        Long expiration = jwtUtil.getExpiration(resolveAccessToken);
        redisTemplate.opsForValue()
                .set(resolveAccessToken, "logout", expiration, TimeUnit.MILLISECONDS);
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
    public Admin findByAdminname(String name) {
        return adminRepository.findByAdminName(name);
    }
    public Seller findBySellername(String name) {
        return sellerRepository.findBySellerName(name);
    }
    public TokenResponseDto removeDuplicated(String accessToken, String refreshToken1) {
        //redis
        String resolvedFreshToken = jwtUtil.resolveRefreshToken(refreshToken1);
        Long tokenExpiration = jwtUtil.getExpiration(resolvedFreshToken);
        Authentication authentication = jwtUtil.getAuthentication(resolvedFreshToken);
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), resolvedFreshToken, tokenExpiration, TimeUnit.MILLISECONDS);
        return new TokenResponseDto(accessToken, refreshToken1);
    }
}


