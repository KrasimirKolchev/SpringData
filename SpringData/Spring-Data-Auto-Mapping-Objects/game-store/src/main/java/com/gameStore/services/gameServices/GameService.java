package com.gameStore.services.gameServices;

import com.gameStore.persistence.models.dtos.gameDTOs.*;
import com.gameStore.persistence.models.entities.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {

    void addGame(AddGameDTO game);

    String deleteGameById(long id);

    boolean gameExists(long id);

    void updateGame(long id, EditGameDTO editGameDTO);

    EditGameDTO getEditGameDTOById(long id);

    List<ViewGameTitlePriceDTO> getAllGamesTitlePrice();

    ViewGameDetailsDTO getViewGameDetailsByTitle(String title);

    Game getGameByTitle(String title);
}
