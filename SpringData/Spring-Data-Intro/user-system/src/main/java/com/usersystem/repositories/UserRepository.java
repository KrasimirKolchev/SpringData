package com.usersystem.repositories;

import com.usersystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    List<User> getUsersByEmailEndingWith(String pattern);

    void deleteUsersByIsDeletedIsTrue();

    List<User> getUsersByLastTimeLoggedInAfter(LocalDate date);


}
