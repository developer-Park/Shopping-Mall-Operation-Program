package com.example.secondteamproject.service.impl;


import com.example.secondteamproject.dto.token.TokenResponseDto;
import com.example.secondteamproject.dto.user.SigninRequestDto;
import com.example.secondteamproject.dto.user.SignupRequestDto;
import com.example.secondteamproject.entity.Admin;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.entity.UserRoleEnum;
import com.example.secondteamproject.jwt.JwtUtil;
import com.example.secondteamproject.dto.user.LogOutRequestDTO;
import com.example.secondteamproject.repository.AdminRepository;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final AdminRepository adminRepository;

    private final RedisTemplate redisTemplate;

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

        //사용자가 비어있으면 운영자인지 확인
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

            System.out.println("################액세스토큰##############" + accessToken);
            System.out.println("################리프레쉬토큰#############" + accessToken);

            //redis
            String resolvedFreshToken = jwtUtil.resolveRefreshToken(refreshToken1);

            Long tokenExpiration = jwtUtil.getExpiration(resolvedFreshToken);
            System.out.println("토큰익스파이어###################" + tokenExpiration);
            Authentication authentication = jwtUtil.getAuthentication(resolvedFreshToken);
            System.out.println("#####Authentication###################" + authentication);

            redisTemplate.opsForValue()
                    .set("RT:" + authentication.getName(), resolvedFreshToken, tokenExpiration, TimeUnit.MILLISECONDS);
            //

            return new TokenResponseDto(accessToken, refreshToken1);
        }
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        String accessToken = jwtUtil.createToken(user.getUsername(), user.getRole());
        String refreshToken1 = jwtUtil.refreshToken(user.getUsername(), user.getRole());


        //redis
        String resolvedFreshToken = jwtUtil.resolveRefreshToken(refreshToken1);

        Long tokenExpiration = jwtUtil.getExpiration(resolvedFreshToken);
        System.out.println("토큰익스파이어###################" + tokenExpiration);
        Authentication authentication = jwtUtil.getAuthentication(resolvedFreshToken);
        System.out.println("#####Authentication###################" + authentication);

        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), resolvedFreshToken, tokenExpiration, TimeUnit.MILLISECONDS);
        //
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

        //redis

        String resolvedFreshToken = jwtUtil.resolveRefreshToken(refreshToken1);

        Long tokenExpiration = jwtUtil.getExpiration(resolvedFreshToken);
        System.out.println("토큰익스파이어###################" + tokenExpiration);
        Authentication authentication = jwtUtil.getAuthentication(resolvedFreshToken);
        System.out.println("#####Authentication###################" + authentication);
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());
        if(ObjectUtils.isEmpty(refreshToken)) {
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

    ////
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public Admin findByAdminname(String name) {
        return adminRepository.findByAdminName(name);
    }


    //redis
    @Transactional
    public void logout(LogOutRequestDTO logout) {
        // 1. Access Token 검증
        String resolveAccessToken = jwtUtil.resolveAccessToken(logout.getAccessToken());
        System.out.println("############리졸브토큰##############" + resolveAccessToken);
        if (!jwtUtil.validateToken(resolveAccessToken)) {
            throw new IllegalArgumentException("Wrong request");
        }

        // 2. Access Token 에서 Username 을 가져옵니다.
        Authentication authentication = jwtUtil.getAuthentication(resolveAccessToken);

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            System.out.println("##############3레디스채크###########"+redisTemplate.delete("RT:" + authentication.getName()));
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtUtil.getExpiration(resolveAccessToken);
        System.out.println("##############3레디스채크###########"+ expiration);
        redisTemplate.opsForValue()
                .set(resolveAccessToken, "logout", expiration, TimeUnit.MILLISECONDS);

    }


}
