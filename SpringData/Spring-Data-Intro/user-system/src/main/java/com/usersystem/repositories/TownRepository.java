package com.usersystem.repositories;

import com.usersystem.models.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TownRepository extends JpaRepository<Town, Long> {
    Town getTownById(Long id);
}
