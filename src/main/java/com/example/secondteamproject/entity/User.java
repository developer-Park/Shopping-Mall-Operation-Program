package com.example.secondteamproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    private String img;
    @Column(nullable = false)
    private String nickname;
    public User(String username, String password, UserRoleEnum role,String img, String nickname) {
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
}
