package com.AccountSystem.accountsystem.services;

import com.AccountSystem.accountsystem.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void registerUser(User user);

    User getUser(Long id);
}
