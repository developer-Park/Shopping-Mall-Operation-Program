package com.example.secondteamproject.security;

import com.example.secondteamproject.entity.Admin;
import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.repository.AdminRepository;
import com.example.secondteamproject.repository.SellerRepository;
import com.example.secondteamproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final SellerRepository sellerRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user ==null){
//            Admin admin = adminRepository.findByAdminName(username);
//
//            return new UserDetailsImpl(admin, admin.getAdminName());
//
//        }
//
//        return new UserDetailsImpl(user, user.getUsername());
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        Admin admin = adminRepository.findByAdminName(username);
        Seller seller = sellerRepository.findBySellerName(username);
        if (admin != null) {return new UserDetailsImpl(admin, admin.getAdminName());}
        if (seller != null) {return new UserDetailsImpl(seller, seller.getSellerName());}
        if (user != null) {return new UserDetailsImpl(user, user.getUsername());}
        return null;
    }
}