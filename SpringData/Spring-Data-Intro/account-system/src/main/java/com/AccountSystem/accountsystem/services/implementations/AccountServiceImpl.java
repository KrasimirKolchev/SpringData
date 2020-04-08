package com.AccountSystem.accountsystem.services.implementations;

import com.AccountSystem.accountsystem.models.Account;
import com.AccountSystem.accountsystem.repositories.AccountRepository;
import com.AccountSystem.accountsystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Account account = validateMoneyAndAccount(money, id);

        if (account.getBalance().compareTo(money) < 0 ) {
            throw new IllegalArgumentException("Not enough money in the account!");
        }

        account.setBalance(account.getBalance().subtract(money));

        this.accountRepository.save(account);
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        Account account = validateMoneyAndAccount(money, id);

        account.setBalance(account.getBalance().add(money));

        this.accountRepository.save(account);
    }

    @Override
    public void registerAccount(Account account) {
        this.accountRepository.save(account);
    }

    private Account validateMoneyAndAccount(BigDecimal money, Long id) {
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Money must be positive number!");
        }

        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invalid account id!"));

        if (account.getUser() == null) {
            throw new EntityNotFoundException("Account is without user!");
        }

        return account;
    }
}
