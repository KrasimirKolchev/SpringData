package com.AccountSystem.accountsystem.services.implementations;

import com.AccountSystem.accountsystem.models.User;
import com.AccountSystem.accountsystem.repositories.UserRepository;
import com.AccountSystem.accountsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        if (this.userRepository.findByUsername(user.getUsername()) != null) {
            throw new EntityExistsException("User already exists in the Database!");
        }
        this.userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id!"));
    }
}
