package com.gameStore.services.gameServices;

import com.gameStore.persistence.models.dtos.gameDTOs.*;
import com.gameStore.persistence.models.entities.Game;
import com.gameStore.persistence.repositories.GamesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.gameStore.common.CommandMessages.*;

@Service
@Transactional
public class GameServiceImpl implements GameService{
    private final GamesRepository gamesRepository;
    private ModelMapper modelMapper;

    public GameServiceImpl(GamesRepository gamesRepository, ModelMapper modelMapper) {
        this.gamesRepository = gamesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addGame(AddGameDTO addGameDTO) {
        Game game = this.modelMapper.map(addGameDTO, Game.class);
        this.gamesRepository.save(game);
    }

    @Override
    public String deleteGameById(long id) {
        if (!this.gameExists(id)) {
            return String.format(GAME_ID_DOES_NOT_EXIST, id);
        }

        String title = this.gamesRepository.getOne(id).getTitle();
        this.gamesRepository.deleteById(id);
        return String.format(DELETED_GAME, title);

    }

    @Override
    public boolean gameExists(long id) {
        return this.gamesRepository.existsById(id);
    }

    @Override
    public void updateGame(long id, EditGameDTO editGameDTO) {
        Game game = modelMapper.map(editGameDTO, Game.class);
        game.setId(id);
        this.gamesRepository.save(game);
    }

    @Override
    public EditGameDTO getEditGameDTOById(long id) {
        Game game = this.gamesRepository.getOne(id);

        EditGameDTO editGameDTO = this.modelMapper.map(game, EditGameDTO.class);

        return editGameDTO;
    }

    @Override
    public List<ViewGameTitlePriceDTO> getAllGamesTitlePrice() {
        List<Game> games = this.gamesRepository.findAll();
        if (games.isEmpty()) {
            throw new EntityNotFoundException(NO_GAMES_IN_DB);
        }

        List<ViewGameTitlePriceDTO> viewGameTitlePriceDTOS = games
                .stream()
                .map(g -> modelMapper.map(g, ViewGameTitlePriceDTO.class))
                .collect(Collectors.toList());

        return viewGameTitlePriceDTOS;
    }

    @Override
    public ViewGameDetailsDTO getViewGameDetailsByTitle(String title) {
        Game game = this.gamesRepository.getGameByTitle(title);
        ViewGameDetailsDTO viewGameDetailsDTO = this.modelMapper.map(game, ViewGameDetailsDTO.class);
        return viewGameDetailsDTO;
    }

    @Override
    public Game getGameByTitle(String title) {
        Game game = this.gamesRepository.getGameByTitle(title);
        if (game == null) {
            throw new EntityNotFoundException(String.format(GAME_NAME_DOES_NOT_EXIST, title));
        }
        return game;
    }
}
