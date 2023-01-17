package com.example.secondteamproject.userpackage;

import com.example.secondteamproject.userpackage.requestDTO.SellerRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UpdateUserRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UserRequestFormDTO;
import com.example.secondteamproject.userpackage.responseDTO.*;

import java.util.List;

public interface UserService {
    UserResponseFormDTO createRequestFormToSeller(UserRequestFormDTO requestDto, Long userId, Long itemId);

    void createSellerRequestForm(SellerRequestDTO requestDto, Long userId);

    UserResponseDTO getOneUser(Long id);

    void updateUserNickName(UpdateUserRequestDTO requestDto, Long id);

    List<AllSellerListResponseDTO> getAllSellerList();

    List<AllItemListResponseDTO> getAllItemList();

    OneSellerResponseDTO getOneSeller(Long sellerid);
}
