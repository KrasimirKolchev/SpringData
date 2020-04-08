package com.gameStore.services.userServices;


import com.gameStore.persistence.models.dtos.gameDTOs.GameTitleDTO;
import com.gameStore.persistence.models.dtos.userDTOs.*;
import com.gameStore.persistence.models.entities.*;
import com.gameStore.persistence.models.entities.enums.Role;
import com.gameStore.persistence.repositories.UsersRepository;
import com.gameStore.services.gameServices.GameService;
import com.gameStore.services.orderServices.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gameStore.common.CommandMessages.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private GameService gameService;
    private OrderService orderService;
    private UserDTO userDto;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper, GameService gameService, OrderService orderService) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    public boolean isUserAdmin() {
        return this.userDto.getRole().equals(Role.ADMIN);
    }

    @Override
    public boolean isUserLoggedIn() {
        return this.userDto.isLoggedIn();
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        if (this.usersRepository.findByEmail(userRegisterDTO.getEmail()) != null) {
            throw new EntityExistsException(USER_ALREADY_EXIST);
        }

        User user = this.modelMapper.map(userRegisterDTO, User.class);
        user.setRole(this.usersRepository.count() > 0 ? Role.USER : Role.ADMIN);
        this.usersRepository.save(user);
    }

    @Override
    public User findById(long id) {
        return this.usersRepository.findById(id).orElse(null);
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        User user = this.usersRepository
                .findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());

        if (user == null) {
            throw new IllegalArgumentException(INCORRECT_USERNAME_PASSWORD);
        } else {
            this.userDto = this.modelMapper.map(user, UserDTO.class);
            this.usersRepository.setUserLoggedIn(user.getId());

        }
        return String.format(SUCCESSFUL_LOGIN, user.getFullName());
    }

    @Override
    public String logoutUser() {
        if (this.userDto == null) {
            return USER_CANNOT_LOGOUT;
        } else {
            String name = this.userDto.getFullName();
            this.usersRepository.setUserLoggedOut(this.userDto.getEmail());
            this.userDto = null;
            return String.format(USER_LOGOUT, name);
        }
    }

    @Override
    public String addGameToShoppingCart(String title) {
        Game game = this.gameService.getGameByTitle(title);
        this.userDto.getGames().add(game);
        return String.format(ADDED_GAME_TO_CART, title);
    }

    @Override
    public String removeGameFromShoppingCart(String title) {
        Set<Game> games = this.userDto.getGames();
        Game shoppingCartGame = null;

        for (Game g: games) {
            if (g.getTitle().equals(title)) {
                shoppingCartGame = g;
            }
        }

        if (shoppingCartGame == null) {
            return String.format(GAME_NOT_IN_SHOPPING_CART, title);
        }

        this.userDto.getGames().remove(shoppingCartGame);
        return String.format(GAME_REMOVED_FROM_CART, shoppingCartGame.getTitle());
    }

    @Override
    public String buyGames() {
        Set<Game> games = this.userDto.getGames();
        if (games.isEmpty()) {
            return NO_GAMES_IN_CART;
        }
        Order order = new Order();
        order.setPurchaseDate(LocalDate.now());

        User user = this.usersRepository.findByEmail(this.userDto.getEmail());
        games.forEach(g -> g.getOwners().add(user));
        user.getGames().addAll(games);

        order.setProducts(games);
        order.setUser(user);
        this.orderService.register(order);

        user.getOrders().add(order);
        this.usersRepository.save(user);
        this.userDto.setGames(new HashSet<>());

        StringBuilder sb = new StringBuilder("Successfully bought games:");
        games.forEach(game -> sb.append("\n\t-").append(game.getTitle()));

        return sb.toString();
    }

    @Override
    public String printOwnedGames() {
        List<GameTitleDTO> gameTitleDTO = this.usersRepository.findByEmail(userDto.getEmail())
                .getGames()
                .stream()
                .map(g -> this.modelMapper.map(g, GameTitleDTO.class))
                .collect(Collectors.toList());

        if (gameTitleDTO.isEmpty()) {
            return NO_OWNED_GAMES;
        }

        return gameTitleDTO.stream()
                .map(g -> String.format("%s", g.getTitle()))
                .collect(Collectors.joining("\n"));
    }
}
