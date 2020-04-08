package com.usersystem.runner;

import com.usersystem.models.*;
import com.usersystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private AlbumService albumService;
    private ColorService colorService;
    private CountryService countryService;
    private PictureService pictureService;
    private TownService townService;
    private UserService userService;

    @Autowired
    public ConsoleRunner(AlbumService albumService, ColorService colorService, CountryService countryService,
                         PictureService pictureService, TownService townService, UserService userService) {
        this.albumService = albumService;
        this.colorService = colorService;
        this.countryService = countryService;
        this.pictureService = pictureService;
        this.townService = townService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

//        createColors();
//        createPictures();
//        createCountries();
//        createTowns();
//        createUsers();
//        createAlbums();

//        String pattern = "@gmail.com";
//        List<User> usersList = getUsersByEmailProvider(pattern);
//        if (usersList.size() != 0) {
//            usersList.forEach(u -> System.out.println(u.toString() + System.lineSeparator()));
//        } else {
//            System.out.println("No users found with email domain " + pattern);
//        }

        User user = new User();
        user.setUsername("User1Name1");
        user.setFirstName("FirstName1");
        user.setLastName("LastName1");
        user.setAge(18);
        user.setEmail("user-test1@abv.bg");
        user.setAlbums(new HashSet<>());
        user.setFriends(new HashSet<>());
        user.setIsDeleted(false);
        user.setPassword("Asd12%2asd1");
        user.setRegisteredOn(LocalDate.of(2001, 1, 1));
        user.setLastTimeLoggedIn(LocalDate.of(2002, 1, 1));
        userService.registerUser(user);

        User user1 = new User();
        user1.setUsername("User1Name2");
        user1.setFirstName("FirstName2");
        user1.setLastName("LastName2");
        user1.setAge(18);
        user1.setEmail("user-test2@abv.bg");
        user1.setAlbums(new HashSet<>());
        user1.setFriends(new HashSet<>());
        user1.setIsDeleted(false);
        user1.setPassword("Asd12%2asd2");
        user1.setRegisteredOn(LocalDate.of(2006, 1, 1));
        user1.setLastTimeLoggedIn(LocalDate.of(2007, 1, 1));
        userService.registerUser(user1);

        //метода за триене в момента работи с не пълна таблица с данни само с двата user-a
        LocalDate date;
        date = LocalDate.of(2005, 1, 1);
        System.out.println("Deleted users count: " + removeInactiveUsers(date));

    }

    private int removeInactiveUsers(LocalDate date) {
        List<User> users = this.userService.getUsersByLastTimeLoggedInAfter(date);
        users.forEach(u -> u.setIsDeleted(true));
        this.userService.registerUsers(users);
        this.userService.deleteUsersByIsDeletedIsTrue();

        return users.size();
    }

    private List<User> getUsersByEmailProvider(String pattern) {
        return this.userService.getAllUsersByEmailProvider(pattern);
    }

    private void createUsers() {
        List<User> newUsers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("User1Name" + i);
            user.setFirstName("FirstName" + i);
            user.setLastName("LastName" + i);
            user.setAge(18 + i);
            user.setEmail("user-test" + i + "@abv.bg");
            user.setAlbums(new HashSet<>());
            user.setFriends(new HashSet<>());
            user.setIsDeleted(false);
            user.setPassword("Asd12%2asd" + i);
            user.setRegisteredOn(LocalDate.of(2000 + i, 1, 1));
            user.setLastTimeLoggedIn(LocalDate.of(2000 + i + 1, 1, 1));
            user.setBornTown(townService.getTownById((long) i + 1));
            user.setLivingTown(townService.getTownById((10L - i)));

            newUsers.add(user);
        }

        this.userService.registerUsers(newUsers);
    }

    private void createCountries() {
        List<Country> newCountries = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Country country = new Country();
            country.setName("CountryName" + i);
            country.setTowns(new HashSet<>());

            newCountries.add(country);
        }

        this.countryService.registerAllCountries(newCountries);
    }

    private void createTowns() {
        List<Town> newTowns = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Town town = new Town();
            town.setName("TownName" + i);
            town.setBornIn(new HashSet<>());
            town.setLivingIn(new HashSet<>());
            town.setCountry(this.countryService.getCountryById((long) (i / 2) + 1));

            newTowns.add(town);
        }
        this.townService.registerAllTowns(newTowns);
    }

    private void createColors() {
        Color color = new Color();
        color.setName("White");
        this.colorService.registerColor(color);

        Color color1 = new Color();
        color1.setName("Green");
        this.colorService.registerColor(color1);

        Color color2 = new Color();
        color2.setName("Red");
        this.colorService.registerColor(color2);
    }

    private void createPictures() {
        List<Picture> newPictures = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Picture picture = new Picture();
            picture.setTitle("Picture Title " + i);
            picture.setPath("path");
            newPictures.add(picture);
        }

        this.pictureService.registerAllPictures(newPictures);
    }

    private void createAlbums() {
        List<Album> newAlbums = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            boolean isPublic = (i % 2 == 0);
            int userId = i / 3;
            Album album = new Album();
            album.setName("Album " + i);
            album.setBackgroundColor(this.colorService.getColorById(1L));
            album.setPublic(isPublic);
            album.setUser(this.userService.getUserById((long) userId + 1));
            Set<Picture> pictures = new HashSet<>();
            pictures.add(pictureService.getPictureById((long) i));
            pictures.add(pictureService.getPictureById((pictureService.getAllPicturesSize() - 1) - i));
            album.setPictures(pictures);

            newAlbums.add(album);
        }

        this.albumService.registerAllAlbums(newAlbums);
    }
}
