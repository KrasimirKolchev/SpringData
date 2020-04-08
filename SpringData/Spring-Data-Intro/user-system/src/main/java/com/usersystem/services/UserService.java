package com.usersystem.services;

import com.usersystem.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;


@Service
public interface UserService {
    void registerUser(User user);

    void registerUsers(List<User> users);

    List<User> getAllUsersByEmailProvider(String pattern);

    void deleteUserById(Long id);

    User getUserById(Long id);

    List<User> getUsersByLastTimeLoggedInAfter(LocalDate date);

    void deleteUsersByIsDeletedIsTrue();
}
