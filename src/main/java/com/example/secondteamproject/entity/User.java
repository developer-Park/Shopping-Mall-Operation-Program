package com.example.secondteamproject.entity;

import com.example.secondteamproject.userpackage.requestDTO.UpdateUserRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;


    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private String img;
    private String imgPath;

    private String nickname;

    private String email;
    public User(String username, String password, UserRoleEnum role, String img, String nickname) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.nickname=nickname;
        this.img=img;
    }

    /**
     * Writer By Park
     * @param username
     * @return true :Equal Entity username and parameter username
     *         false : Not match Entity username and parameter username
     */
    public boolean isWriter(String username) {
        return getUsername().equals(username);
    }
    public void update(UpdateUserRequestDTO updateUserRequestDTO){
        this.nickname = updateUserRequestDTO.getNickname();
    }
}
