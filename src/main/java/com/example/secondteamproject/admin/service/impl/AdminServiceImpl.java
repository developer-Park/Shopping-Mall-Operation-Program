package com.example.secondteamproject.admin.service.impl;

import com.example.secondteamproject.admin.dto.CustomerListResponseDto;
import com.example.secondteamproject.admin.dto.SellerListResponseDto;
import com.example.secondteamproject.admin.dto.SellerRequestListResponseDto;
import com.example.secondteamproject.admin.dto.SellerRequestResponseDto;
import com.example.secondteamproject.dto.user.LogOutRequestDTO;
import com.example.secondteamproject.seller.entity.Seller;
import com.example.secondteamproject.entity.SellerRequest;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.entity.UserRoleEnum;
import com.example.secondteamproject.repository.SellerRepository;
import com.example.secondteamproject.repository.SellerRequestRepository;
import com.example.secondteamproject.repository.UserRepository;
import com.example.secondteamproject.admin.service.AdminService;
import com.example.secondteamproject.service.GeneralService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final SellerRequestRepository sellerRequestRepository;
    public List<CustomerListResponseDto> getAllCustomer(Pageable pageable) {
        List<CustomerListResponseDto> customerList = new ArrayList<>();
        for (User user : userRepository.findAll(pageable)) {
            customerList.add(new CustomerListResponseDto(user));
        }
        return customerList;
    }

    public List<SellerListResponseDto> getAllSeller(Pageable pageable) {
        List<SellerListResponseDto> sellerList = new ArrayList<>();
        for (Seller seller : sellerRepository.findAll(pageable)) {
            sellerList.add(new SellerListResponseDto(seller));
        }
        return sellerList;
    }

    public List<SellerRequestListResponseDto> getAllSellerRequest(Pageable pageable) {
        List<SellerRequestListResponseDto> sellerRequestList = new ArrayList<>();
        for (SellerRequest sellerRequest : sellerRequestRepository.findAll(pageable)) {
            sellerRequestList.add(new SellerRequestListResponseDto(sellerRequest));
        }
        return sellerRequestList;
    }


    public SellerRequestResponseDto getSellerRequest(Long requestId) {
        SellerRequest sellerRequest = sellerRequestRepository.findById(requestId).orElseThrow(
                () -> new IllegalArgumentException("해당 요청 글이 존재하지 않습니다.")
        );
        return new SellerRequestResponseDto(sellerRequest);
    }

    @Transactional
    public void approveSellerRequest(Long requestId) {
        SellerRequest sellerRequest = sellerRequestRepository.findById(requestId).orElseThrow(
                () -> new IllegalArgumentException("해당 요청 글이 존재하지 않습니다.")
        );
        if (sellerRequest.isStatusCompleted()) {
            throw new IllegalArgumentException("이미 승인된 요청입니다.");
        }

        User user = sellerRequest.getUser();
        String sellerName = user.getUsername();
        String password = user.getPassword();
        String img = user.getImg();
        String nickname = user.getNickname();
        String email = user.getEmail();
        String description = sellerRequest.getContent();

        Seller seller = new Seller(sellerName, password, img, nickname, email, description);
        sellerRepository.save(seller);
        sellerRequestRepository.delete(sellerRequest);
        userRepository.delete(user);
    }

    @Transactional
    public void deleteSellerRole(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> new IllegalArgumentException("해당 판매자가 존재하지 않습니다.")
        );

        String username = seller.getSellerName();
        String password = seller.getPassword();
        UserRoleEnum role = UserRoleEnum.USER;
        String img = seller.getImg();
        String nickname = seller.getNickname();
        String email = seller.getEmail();

        User user = new User(username, password, role, img, nickname, email);
        userRepository.save(user);

        sellerRepository.delete(seller);
    }
}