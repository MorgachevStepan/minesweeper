package com.stepanew.minesweeper.service.impl;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.entity.Game;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.domain.mapper.GameMapper;
import com.stepanew.minesweeper.exception.GameIsNotFoundException;
import com.stepanew.minesweeper.repository.GameRepository;
import com.stepanew.minesweeper.service.GameStorageService;
import com.stepanew.minesweeper.utils.json.JsonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GameStorageServiceImpl implements GameStorageService {

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    public Game saveGame(NewGameRequest request, Cell[][] actualField, Cell[][] visibleField) {
        String actualFieldJson = JsonUtils.serialize(actualField);
        String visibleFieldJson = JsonUtils.serialize(visibleField);

        Game game = gameMapper.toEntity(request, actualFieldJson, visibleFieldJson);
        return gameRepository.save(game);
    }

    public Game loadGame(UUID gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameIsNotFoundException(gameId));
    }

    public Cell[][] getActualField(Game game) {
        return JsonUtils.deserialize(game.getActualField(), Cell[][].class);
    }

    public Cell[][] getVisibleField(Game game) {
        return JsonUtils.deserialize(game.getVisibleField(), Cell[][].class);
    }

    public void updateGame(Game game, Cell[][] actualField) {
        game.setActualField(JsonUtils.serialize(actualField));
        gameRepository.save(game);
    }
}