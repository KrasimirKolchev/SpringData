package com.usersystem.repositories;

import com.usersystem.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ColorRepository extends JpaRepository<Color, Long> {
    Color getColorById(Long id);
}
