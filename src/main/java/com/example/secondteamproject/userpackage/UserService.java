package com.example.secondteamproject.userpackage;

import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.userpackage.requestDTO.SellerRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UpdateUserRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UserRequestFormDTO;
import com.example.secondteamproject.userpackage.responseDTO.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponseFormDTO createRequestFormToSeller(UserRequestFormDTO requestDto, Long userId, Long itemId);

    void createSellerRequestForm(SellerRequestDTO requestDto, Long userId);

    UserResponseDTO getOneUser(Long id);

    void updateUserNickName(UpdateUserRequestDTO requestDto, Long id);

    List<SellerResponseDTO> getAllSellerList(UserDetailsImpl userDetails, Pageable pageable);

    List<ItemResponseDTO> getAllItemList(UserDetailsImpl userDetails, Pageable pageable);

    SellerResponseDTO getOneSeller(Long sellerid);
}
