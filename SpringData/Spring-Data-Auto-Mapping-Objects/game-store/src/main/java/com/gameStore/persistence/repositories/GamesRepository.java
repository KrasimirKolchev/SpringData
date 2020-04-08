package com.gameStore.persistence.repositories;

import com.gameStore.persistence.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long> {

    Game getGameByTitle(String title);

}
