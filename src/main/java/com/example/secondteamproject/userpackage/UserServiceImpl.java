package com.example.secondteamproject.userpackage;

import com.example.secondteamproject.entity.*;
import com.example.secondteamproject.repository.*;
import com.example.secondteamproject.seller.entity.Item;
import com.example.secondteamproject.seller.entity.Seller;
import com.example.secondteamproject.userpackage.requestDTO.SellerRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UpdateUserRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UserRequestFormDTO;
import com.example.secondteamproject.userpackage.responseDTO.*;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public List<AllSellerListResponseDTO> getAllSellerList() {
        List<Seller> sellers = sellerRepository.findAllByOrderByModifiedAtDesc();
        List<AllSellerListResponseDTO> allSellerListResponseDTO = new ArrayList<>();
        for (Seller seller : sellers){
            allSellerListResponseDTO.add(new AllSellerListResponseDTO(seller));
        }
        return allSellerListResponseDTO;
    }

    @Transactional(readOnly = true)
    public List<AllItemListResponseDTO> getAllItemList() {
        return itemRepository.findAllByOrderByModifiedAtDesc().stream().map(AllItemListResponseDTO::new).collect(Collectors.toList());
    }

    public OneSellerResponseDTO getOneSeller(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> new IllegalArgumentException("Not found user")
        );
        return new OneSellerResponseDTO(seller);
    }
}

