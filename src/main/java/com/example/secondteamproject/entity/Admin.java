package com.example.secondteamproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Getter
@NoArgsConstructor
@Entity(name = "admins")
public class Admin extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMIN_ID")
    private Long id;
    @Column(nullable = false, unique = true)
    private String adminName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private String img;

    private String email;


    public Admin(String adminName, String password, UserRoleEnum role,String img) {
        this.adminName = adminName;
        this.password = password;
        this.role = role;
        this.img=img;
    }

}
