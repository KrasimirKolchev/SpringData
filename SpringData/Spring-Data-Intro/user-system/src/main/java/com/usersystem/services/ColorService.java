package com.usersystem.services;

import com.usersystem.models.Color;
import org.springframework.stereotype.Service;

@Service
public interface ColorService {
    void registerColor(Color color);

    void deletById(Long id);

    Color getColorById(Long id);
}
