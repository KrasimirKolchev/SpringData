package com.gameStore.services.userServices;

import com.gameStore.persistence.models.dtos.userDTOs.UserLoginDTO;
import com.gameStore.persistence.models.dtos.userDTOs.UserRegisterDTO;
import com.gameStore.persistence.models.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void register(UserRegisterDTO userRegisterDTO);

    User findById(long id);

    String loginUser(UserLoginDTO userLoginDTO);

    String logoutUser();

    boolean isUserAdmin();

    boolean isUserLoggedIn();

    String addGameToShoppingCart(String title);

    String removeGameFromShoppingCart(String title);

    String buyGames();

    String printOwnedGames();
}
