package com.gameStore.controller;

import com.gameStore.constraints.validators.ValidationUtil;
import com.gameStore.persistence.models.dtos.gameDTOs.*;
import com.gameStore.persistence.models.dtos.userDTOs.*;
import com.gameStore.services.gameServices.GameService;
import com.gameStore.services.userServices.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.gameStore.common.CommandMessages.*;

@Component
public class GameStoreApp implements CommandLineRunner {
    private BufferedReader reader;
    private ValidationUtil validationUtil;
    private UserService userService;
    private GameService gameService;
    private ModelMapper modelMapper;

    @Autowired
    public GameStoreApp(BufferedReader reader, ValidationUtil validationUtil, UserService userService,
                        GameService gameService, ModelMapper modelMapper) {
        this.reader = reader;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("Enter command:");
            String[] input = this.reader.readLine().split("\\|");

            String command = input[0];

            if (command.equals("End")) {
                break;
            }

            String[] data = Arrays.copyOfRange(input, 1, input.length);

            try {
                switch (command) {
                    case "RegisterUser":
                        System.out.println(registerUser(data));
                        break;
                    case "LoginUser":
                        System.out.println(loginUser(data));
                        break;
                    case "Logout":
                        System.out.println(logoutUser());
                        break;
                    case "AddGame":
                        System.out.println(addGame(data));
                        break;
                    case "EditGame":
                        System.out.println(editGame(data));
                        break;
                    case "DeleteGame":
                        System.out.println(deleteGame(data[0]));
                        break;
                    case "AllGames":
                        System.out.println(viewAllGames());
                        break;
                    case "DetailGame":
                        System.out.println(viewGameDetails(data));
                        break;
                    case "OwnedGames":
                        System.out.println(printOwnedGames());
                        break;
                    case "AddItem":
                        System.out.println(addGameToShoppingCart(data[0]));
                        break;
                    case "RemoveItem":
                        System.out.println(removeFromShoppingCart(data[0]));
                        break;
                    case "BuyItem":
                        System.out.println(buyItem());
                        break;
                    default:
                        System.out.println("Invalid command!");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
    }

    private String printOwnedGames() {
        if (this.userService.isUserLoggedIn()) {
            return DEFAULT_IS_LOGGED + PRINT_GAMES;
        }
        return this.userService.printOwnedGames();
    }

    private String buyItem() {
        if (this.userService.isUserLoggedIn()) {
            return DEFAULT_IS_LOGGED + BUY_GAMES;
        }
        return this.userService.buyGames();
    }

    private String removeFromShoppingCart(String title) {
        if (this.userService.isUserLoggedIn()) {
            return DEFAULT_IS_LOGGED + GAME_SHOPPING_CART_REMOVE;
        }
        return this.userService.removeGameFromShoppingCart(title);
    }

    private String addGameToShoppingCart(String title) {
        if (this.userService.isUserLoggedIn()) {
            return DEFAULT_IS_LOGGED + GAME_SHOPPING_CART_ADD;
        }

        try {
            return this.userService.addGameToShoppingCart(title);
        } catch (EntityNotFoundException e) {
            return e.getMessage();
        }
    }

    private String viewGameDetails(String[] data) {
        if (this.userService.isUserLoggedIn()) {
            return DEFAULT_IS_LOGGED + GAME_DETAILS;
        }

        ViewGameDetailsDTO viewGameDetailsDTO = this.gameService.getViewGameDetailsByTitle(data[0]);
        if (viewGameDetailsDTO == null) {
            return String.format(GAME_NAME_DOES_NOT_EXIST, data[0]);
        }

        return viewGameDetailsDTO.toString();
    }

    private String viewAllGames() {
        if (this.userService.isUserLoggedIn()) {
            return DEFAULT_IS_LOGGED + GAMES_LIST;
        }

        StringBuilder sb = new StringBuilder();
        try {
            this.gameService.getAllGamesTitlePrice()
                    .forEach(g -> sb.append(String.format("%s %.2f\n", g.getTitle(), g.getPrice())));
        } catch (EntityNotFoundException e) {
            sb.append(e.getMessage());
        }

        return sb.toString();
    }

    private String editGame(String[] data) {
        if (this.userService.isUserLoggedIn()) {
            return DEFAULT_IS_LOGGED + EDIT_GAME;
        }

        if (!this.userService.isUserAdmin()) {
            return DEFAULT_IS_ADMIN + EDIT_GAME;
        }

        long gameId = Long.parseLong(data[0]);

        if (!this.gameService.gameExists(gameId)) {
            return String.format(GAME_ID_DOES_NOT_EXIST, gameId);
        }

        String[] dataValues = Arrays.copyOfRange(data, 1, data.length);
        EditGameDTO editGameDTO = updateValues(this.gameService.getEditGameDTOById(gameId), dataValues);
        if (validationUtil.isValid(editGameDTO)) {
            this.gameService.updateGame(gameId, editGameDTO);
        } else {
            this.validationUtil.getViolationsData(editGameDTO).stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }

        return String.format(EDITED_GAME, editGameDTO.getTitle());
    }

    private String deleteGame(String gameId) {
        if (this.userService.isUserLoggedIn()) {
            return DEFAULT_IS_LOGGED + DELETE_GAME;
        }

        if (!this.userService.isUserAdmin()) {
            return DEFAULT_IS_ADMIN + DELETE_GAME;
        }
        return this.gameService.deleteGameById(Long.parseLong(gameId));
    }

    private String addGame(String[] data) {
        if (this.userService.isUserLoggedIn()) {
            return DEFAULT_IS_LOGGED + ADD_GAME;
        }

        if (!this.userService.isUserAdmin()) {
            return DEFAULT_IS_ADMIN + ADD_GAME;
        }

        AddGameDTO addGameDTO = new AddGameDTO(data[0], new BigDecimal(data[1]),
                new BigDecimal(data[2]), data[3], data[4], data[5],
                LocalDate.parse(data[6], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        String msg = "";
        if (this.validationUtil.isValid(addGameDTO)) {
            this.gameService.addGame(addGameDTO);
            msg = String.format(ADDED_GAME, data[0]);
        } else {
            this.validationUtil.getViolationsData(addGameDTO).stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        return msg;
    }

    private String logoutUser() {
        return this.userService.logoutUser();
    }

    private String loginUser(String[] data) {
        UserLoginDTO userLoginDTO = new UserLoginDTO(data[0], data[1]);
        try {
            return this.userService.loginUser(userLoginDTO);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private String registerUser(String[] data) {
        if (!data[1].equals(data[2])) {
            return PASSWORDS_DOESNT_MATCH;
        }

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(data[0], data[1], data[3]);
        String msg = "";

        if (validationUtil.isValid(userRegisterDTO)) {
            try {
                this.userService.register(userRegisterDTO);
                msg = String.format(REGISTER_USER, data[3]);
            } catch (EntityExistsException e) {
                msg = e.getMessage();
            }
        } else {
            validationUtil.getViolationsData(userRegisterDTO).stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        return msg;
    }

    private EditGameDTO updateValues(EditGameDTO editGameDTO, String[] data) {

        for (String d : data) {
            String[] tokens = d.split("=");

            switch (tokens[0]) {
                case "title":
                    editGameDTO.setTitle(tokens[1]);
                    break;
                case "price":
                    editGameDTO.setPrice(new BigDecimal(tokens[1]));
                    break;
                case "size":
                    editGameDTO.setSize(new BigDecimal(tokens[1]));
                    break;
                case "trailer":
                    editGameDTO.setVideoId(tokens[1]);
                    break;
                case "thumbnailURL":
                    editGameDTO.setThumbnailURL(tokens[1]);
                    break;
                case "description":
                    editGameDTO.setDescription(tokens[1]);
                    break;
            }
        }

        return editGameDTO;
    }
}
