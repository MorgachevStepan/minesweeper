package com.stepanew.minesweeper.service.impl;

import com.stepanew.minesweeper.domain.entity.Game;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.exception.CellIsOpenedException;
import com.stepanew.minesweeper.service.FieldService;
import com.stepanew.minesweeper.service.GameStateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameStateServiceImpl implements GameStateService {

    private final FieldService fieldService;

    public void processTurn(Game game, Cell[][] actualField, Cell[][] visibleField, int row, int column) {
        validateMove(actualField, row, column);

        Cell selectedCell = visibleField[row][column];

        if (selectedCell == Cell.NOT_FOUNDED) {
            game.setCompleted(true);
            fieldService.revealAllMines(visibleField, actualField);
        } else if (selectedCell == Cell.CELL_0) {
            fieldService.floodFill(actualField, visibleField, row, column);
            actualField[row][column] = selectedCell;
        } else {
            actualField[row][column] = selectedCell;
        }
    }

    public boolean isGameWon(Cell[][] actualField, Cell[][] visibleField) {
        for (int y = 0; y < actualField.length; y++) {
            for (int x = 0; x < actualField[0].length; x++) {
                if (visibleField[y][x] != Cell.NOT_FOUNDED && actualField[y][x] == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public void markGameAsCompleted(Game game) {
        game.setCompleted(true);
    }

    private void validateMove(Cell[][] actualField, int row, int column) {
        if (actualField[row][column] != Cell.EMPTY) {
            throw new CellIsOpenedException();
        }
    }

}