package com.AccountSystem.accountsystem.repositories;

import com.AccountSystem.accountsystem.models.Account;
import com.AccountSystem.accountsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountById(Long id);

    List<Account> findAllByUser(User user);

}
