package com.gameStore.persistence.models.dtos.userDTOs;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterDTO {
    private String email;
    private String password;
    private String fullName;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    @Email
    @Pattern(regexp = "^([a-zA-Z0-9]+[-_\\.]?[a-zA-Z0-9]+)@([a-zA-Z]+-?[a-zA-Z]+)(\\.[a-zA-Z]+[-]?[a-zA-Z]+)+$"
            , message = "Invalid Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}", message = "Invalid password!")
    @Size(min = 6, message = "Password too short!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "Missing full name!")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
