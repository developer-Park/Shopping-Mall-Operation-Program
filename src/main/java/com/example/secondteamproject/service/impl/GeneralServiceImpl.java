package com.example.secondteamproject.service.impl;


import com.example.secondteamproject.dto.token.TokenResponseDto;
import com.example.secondteamproject.dto.user.SigninRequestDto;
import com.example.secondteamproject.dto.user.SignupRequestDto;
import com.example.secondteamproject.entity.Admin;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.entity.UserRoleEnum;
import com.example.secondteamproject.jwt.JwtUtil;
import com.example.secondteamproject.repository.AdminRepository;
import com.example.secondteamproject.repository.UserRepository;
import com.example.secondteamproject.service.GeneralService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GeneralServiceImpl implements GeneralService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final AdminRepository adminRepository;


    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {

        String name = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        //회원 중복 확인
        User foundUser = userRepository.findByUsername(name);
        if (foundUser != null) {
            throw new IllegalArgumentException("Duplicated user");
        }

        Admin admin = adminRepository.findByAdminName(name);
        if (admin != null) {
            throw new IllegalArgumentException("Duplicated admin user");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        // 사용자 role 확인
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("Wrong admin password");
            }
            role = UserRoleEnum.ADMIN;
            Admin createAdmin = new Admin(name, password, role, signupRequestDto.getImg());
            adminRepository.save(createAdmin);

        } else {
            User user = new User(name, password, role, signupRequestDto.getImg(), signupRequestDto.getNickname());
            userRepository.save(user);

        }

    }

    /**
     * Writer by Park
     *
     * @param signinRequestDto
     * @return AccessToken, Refresh Token
     */
    @Transactional(readOnly = true)
    public TokenResponseDto signin(SigninRequestDto signinRequestDto) {
        String username = signinRequestDto.getUsername();
        String password = signinRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username);
        if (user == null) {
            Admin admin = adminRepository.findByAdminName(username);
            if (admin == null) {
                throw new IllegalArgumentException("Not found admin");
            }
            if (!passwordEncoder.matches(password, admin.getPassword())) {
                throw new IllegalArgumentException("Wrong password");
            }
            String accessToken = jwtUtil.createToken(admin.getAdminName(), admin.getRole());
            String refreshToken1 = jwtUtil.refreshToken(admin.getAdminName(), admin.getRole());
            return new TokenResponseDto(accessToken, refreshToken1);
        }
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        String accessToken = jwtUtil.createToken(user.getUsername(), user.getRole());
        String refreshToken1 = jwtUtil.refreshToken(user.getUsername(), user.getRole());

        return new TokenResponseDto(accessToken, refreshToken1);
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

    ////
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public Admin findByAdminname(String name) {
        return adminRepository.findByAdminName(name);
    }





}
