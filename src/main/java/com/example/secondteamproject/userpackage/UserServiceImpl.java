package com.example.secondteamproject.userpackage;

import com.example.secondteamproject.entity.*;
import com.example.secondteamproject.repository.*;
import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.seller.entity.Item;
import com.example.secondteamproject.seller.entity.Seller;
import com.example.secondteamproject.userpackage.requestDTO.SellerRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UpdateUserRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UserRequestFormDTO;
import com.example.secondteamproject.userpackage.responseDTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductInquiryRepository productInquiryRepository;
    private final SellerRequestRepository sellerRequestRepository;
    private final ItemRepository itemRepository;
    private final SellerRepository sellerRepository;

    @Transactional
    public UserResponseFormDTO createRequestFormToSeller(UserRequestFormDTO requestDto, Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("Not found item")
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Not found user")
        );
        ProductInquiry productInquiry = new ProductInquiry(requestDto, user, item);
        productInquiryRepository.save(productInquiry);
        return new UserResponseFormDTO(productInquiry);
    }

    @Transactional
    public void createSellerRequestForm(SellerRequestDTO requestDto, Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Not found user")
        );
        Optional<SellerRequest> checksellerRequestIsPresent = sellerRequestRepository.findByUserId(userId);
        if (checksellerRequestIsPresent.isPresent()) {
            throw new IllegalArgumentException("Exist request seller");
        }
        SellerRequest sellerRequest = new SellerRequest(requestDto,user);
        sellerRequestRepository.save(sellerRequest);
    }


    public UserResponseDTO getOneUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Not found user")
        );
        return new UserResponseDTO(user);
    }

    @Transactional
    public void updateUserNickName(UpdateUserRequestDTO requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Not found user")
        );
        user.update(requestDto);
    }

    // 판매자 리스트 조회, 10개씩 페이징
    @Transactional(readOnly = true)
    public List<SellerResponseDTO> getAllSellerList(UserDetailsImpl userDetails, Pageable pageable) {
        Page<Seller> sellers = sellerRepository.findAll(pageable);
        return sellers
                .stream()
                .map(SellerResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 상품 리스트 조회, 10개씩 페이징
    @Transactional(readOnly = true)
    public List<ItemResponseDTO> getAllItemList(UserDetailsImpl userDetails, Pageable pageable) {
        Page<Item> items = itemRepository.findAll(pageable);
        return items
                .stream()
                .map(ItemResponseDTO::new)
                .collect(Collectors.toList());
    }


    public SellerResponseDTO getOneSeller(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> new IllegalArgumentException("Not found user")
        );
        return new SellerResponseDTO(seller);
    }
}

