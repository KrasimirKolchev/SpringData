package com.usersystem.services;

import com.usersystem.models.Country;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {
    void registerCountry(Country country);

    void registerAllCountries(List<Country> countries);

    void deletById(Long id);

    Country getCountryById(Long id);
}
