package com.gameStore.persistence.models.dtos.userDTOs;

import com.gameStore.persistence.models.entities.Game;
import com.gameStore.persistence.models.entities.enums.Role;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private String fullName;
    private String email;
    private String password;
    private Role role;
    private boolean isLoggedIn;
    private Set<Game> games;

    public UserDTO() {
    }

    public UserDTO(String fullName, String email, String password, Role role, boolean isLoggedIn) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isLoggedIn = isLoggedIn;
        this.games = new HashSet<>();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
