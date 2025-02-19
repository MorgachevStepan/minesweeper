package com.stepanew.minesweeper.service;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.entity.Game;
import com.stepanew.minesweeper.domain.enums.Cell;

import java.util.UUID;

public interface GameStorageService {

    Game saveGame(NewGameRequest request, Cell[][] actualField, Cell[][] visibleField);

    Game loadGame(UUID gameId);

    Cell[][] getActualField(Game game);

    Cell[][] getVisibleField(Game game);

    Game updateGame(Game game, Cell[][] actualField);

}
