package com.usersystem.repositories;

import com.usersystem.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country getCountryById(Long id);
}
