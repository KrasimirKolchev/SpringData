package com.AccountSystem.accountsystem.core;

import com.AccountSystem.accountsystem.models.Account;
import com.AccountSystem.accountsystem.models.User;
import com.AccountSystem.accountsystem.services.AccountService;
import com.AccountSystem.accountsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private UserService userService;
    private AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user =  new User();
        user.setUsername("Ivan");
        user.setAge(22);

        Account account = new Account();
        account.setBalance(new BigDecimal(22000));
        account.setUser(user);
        user.setAccounts(new HashSet<>(){{add(account);}});

        this.userService.registerUser(user);
        this.accountService.registerAccount(account);

        this.accountService.withdrawMoney(new BigDecimal(20000), account.getId());
        this.accountService.transferMoney(new BigDecimal(15000), account.getId());


    }
}
