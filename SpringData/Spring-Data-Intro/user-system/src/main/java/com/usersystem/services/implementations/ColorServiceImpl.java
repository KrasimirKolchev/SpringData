package com.usersystem.services.implementations;

import com.usersystem.models.Color;
import com.usersystem.repositories.ColorRepository;
import com.usersystem.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Autowired
    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public void registerColor(Color color) {
        this.colorRepository.save(color);
    }

    @Override
    public void deletById(Long id) {
        this.colorRepository.deleteById(id);
    }

    @Override
    public Color getColorById(Long id) {
        return this.colorRepository.getColorById(id);
    }
}
