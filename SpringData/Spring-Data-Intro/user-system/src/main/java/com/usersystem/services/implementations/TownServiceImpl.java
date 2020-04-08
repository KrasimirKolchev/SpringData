package com.usersystem.services.implementations;

import com.usersystem.models.Town;
import com.usersystem.repositories.TownRepository;
import com.usersystem.services.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public void registerTown(Town town) {
        this.townRepository.save(town);
    }

    @Override
    public void registerAllTowns(List<Town> towns) {
        this.townRepository.saveAll(towns);
    }

    @Override
    public void deleteById(Long id) {
        this.townRepository.deleteById(id);
    }

    @Override
    public Town getTownById(Long id) {
        return this.townRepository.getTownById(id);
    }
}
