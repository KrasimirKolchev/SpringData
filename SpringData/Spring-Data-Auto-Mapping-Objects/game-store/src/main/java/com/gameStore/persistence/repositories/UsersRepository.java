package com.gameStore.persistence.repositories;

import com.gameStore.persistence.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    User findByEmailAndPassword(String email, String password);

    @Modifying
    @Query("update com.gameStore.persistence.models.entities.User as u set u.loggedIn = 1 where u.id = :id")
    void setUserLoggedIn(@Param(value = "id") long id);

    @Modifying
    @Query("update com.gameStore.persistence.models.entities.User as u set u.loggedIn = 0 where u.email = :email")
    void setUserLoggedOut(@Param(value = "email") String email);

    User findByEmail(String email);

}
