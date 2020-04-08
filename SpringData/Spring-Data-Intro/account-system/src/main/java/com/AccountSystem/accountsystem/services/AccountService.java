package com.AccountSystem.accountsystem.services;

import com.AccountSystem.accountsystem.models.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface AccountService {
    void withdrawMoney(BigDecimal money, Long id);
    void transferMoney(BigDecimal money, Long id);
    void registerAccount(Account account);
}
