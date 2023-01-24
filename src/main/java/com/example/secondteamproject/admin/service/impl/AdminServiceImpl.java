package com.example.secondteamproject.admin.service.impl;

import com.example.secondteamproject.admin.dto.CustomerListResponseDto;
import com.example.secondteamproject.admin.dto.SellerListResponseDto;
import com.example.secondteamproject.admin.dto.SellerRequestListResponseDto;
import com.example.secondteamproject.admin.dto.SellerRequestResponseDto;
import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.entity.SellerRequest;
import com.example.secondteamproject.entity.User;
import com.example.secondteamproject.entity.UserRoleEnum;
import com.example.secondteamproject.repository.SellerRepository;
import com.example.secondteamproject.repository.SellerRequestRepository;
import com.example.secondteamproject.repository.UserRepository;
import com.example.secondteamproject.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<SellerListResponseDto> getAllSellersBySearching(Pageable pageable, String searchOption, String keyword) {
        String[] keywordArr = keyword.split(" ");
        // 스페이스 기준으로 keyword 분리

        //분리된 키워드는 OR 조건으로 검색됨
        List<Seller> sellerList = new ArrayList<>();
        for (String key : keywordArr) {
            List<Seller> sellerRepoTemp = switch (searchOption) {
                case "이름" -> sellerRepository.findAllBySellerNameContainsIgnoreCase(key);
                case "별명" -> sellerRepository.findAllByNicknameContainsIgnoreCase(key);
                case "이메일" -> sellerRepository.findAllByEmailContainsIgnoreCase(key);
                case "이름별명" ->
                        sellerRepository.findAllBySellerNameContainsIgnoreCaseOrNicknameContainsIgnoreCase(key, key);
                default -> sellerRepository.findAll();
            };
            sellerList.addAll(sellerRepoTemp);
        }

        Page<Seller> sellerPage = new PageImpl<>(sellerList, pageable, sellerList.size());//페이징

        return sellerPage.stream()
                .distinct()//중복제거
                .map(SellerListResponseDto::new)
                .collect(Collectors.toList());
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

        User user = sellerRequest.getUser();
        String description = sellerRequest.getContent();

        Seller seller = new Seller(user, description);
        sellerRepository.save(seller);
        sellerRequestRepository.delete(sellerRequest);
        userRepository.delete(user);
    }

    @Transactional
    public void approveAllSellerRequest() {
        List<SellerRequest> sellerRequestList = sellerRequestRepository.findAll();

        for (SellerRequest sellerRequest : sellerRequestList) {
            User user = sellerRequest.getUser();
            String description = sellerRequest.getContent();
            Seller seller = new Seller(user, description);

            sellerRepository.save(seller);
            sellerRequestRepository.delete(sellerRequest);
            userRepository.delete(user);
        }
    }

    @Transactional
    public void deleteSellerRole(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> new IllegalArgumentException("해당 판매자가 존재하지 않습니다.")
        );

        String username = seller.getSellerName();
        String password = seller.getPassword();
        UserRoleEnum role = UserRoleEnum.USER;
        String nickname = seller.getNickname();
        String email = seller.getEmail();

        User user = new User(username, password, role, nickname, email);
        userRepository.save(user);

        sellerRepository.delete(seller);
    }
}