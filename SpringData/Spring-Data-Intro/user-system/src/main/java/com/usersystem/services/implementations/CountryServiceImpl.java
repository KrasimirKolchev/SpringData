package com.usersystem.services.implementations;

import com.usersystem.models.Country;
import com.usersystem.repositories.CountryRepository;
import com.usersystem.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public void registerCountry(Country country) {
        this.countryRepository.save(country);
    }

    @Override
    public void registerAllCountries(List<Country> countries) {
        this.countryRepository.saveAll(countries);
    }

    @Override
    public void deletById(Long id) {
        this.countryRepository.deleteById(id);
    }

    @Override
    public Country getCountryById(Long id) {
        return this.countryRepository.getCountryById(id);
    }

}
