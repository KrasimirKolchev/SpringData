package com.usersystem.services.implementations;

import com.usersystem.models.User;
import com.usersystem.repositories.UserRepository;
import com.usersystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void registerUsers(List<User> users) {
        this.userRepository.saveAll(users);
    }

    @Override
    public List<User> getAllUsersByEmailProvider(String pattern) {
        return this.userRepository.getUsersByEmailEndingWith(pattern);
    }

    @Override
    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findUserById(id);
    }

    @Override
    public List<User> getUsersByLastTimeLoggedInAfter(LocalDate date) {
        return this.userRepository.getUsersByLastTimeLoggedInAfter(date);
    }

    @Override
    public void deleteUsersByIsDeletedIsTrue() {
        this.userRepository.deleteUsersByIsDeletedIsTrue();
    }
}
