package com.stepanew.minesweeper.service;

import com.stepanew.minesweeper.domain.entity.Game;
import com.stepanew.minesweeper.domain.enums.Cell;

public interface GameStateService {

    void processTurn(Game game, Cell[][] actualField, Cell[][] visibleField, int row, int column);

    boolean isGameWon(Cell[][] actualField, Cell[][] visibleField);

    void markGameAsCompleted(Game game);

}
