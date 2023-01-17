package com.example.secondteamproject.security;

import com.example.secondteamproject.entity.Admin;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.entity.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {


    private User user;
    private final String username;
    private Admin admin;

    public UserDetailsImpl(User user, String username) {
        this.user = user;
        this.username = username;
    }

    public UserDetailsImpl(Admin admin, String username) {
        this.admin = admin;
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public Admin getAdmin() {
        return admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user == null) {
            UserRoleEnum adminRole = admin.getRole();
            String authority = adminRole.getAuthority();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(simpleGrantedAuthority);

            return authorities;
        } else {
            UserRoleEnum role = user.getRole();
            String authority = role.getAuthority();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(simpleGrantedAuthority);
            return authorities;
        }
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}