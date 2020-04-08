package com.usersystem.services;

import com.usersystem.models.Town;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TownService {
    void registerTown(Town town);

    void registerAllTowns(List<Town> towns);

    void deleteById(Long id);

    Town getTownById(Long id);
}
