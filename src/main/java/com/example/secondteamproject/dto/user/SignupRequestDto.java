package com.example.secondteamproject.dto.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//email 변수 추가
@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {
    @NotEmpty(message = "아이디는 필수 입력값입니다.")
    @Size(min = 4, max = 10, message = "최소 4자 이상 10자 이하여야 함")
    @Pattern(regexp  = "^[a-z0-9]*$", message = "알파벳 소문자와 숫자로만 구성되야 함")
    private String username;
    @NotEmpty(message = "암호는 필수 입력값입니다.")
    @Size(min = 8, max = 15, message = "최소 8자 이상 15자 이하여야 함")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "알파벳 대소문자와 숫자로만 구성되야 함")
    private String password;

    private String nickname;

    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Email
    private String email;

    private boolean admin = false;
    private String adminToken = "";
}
