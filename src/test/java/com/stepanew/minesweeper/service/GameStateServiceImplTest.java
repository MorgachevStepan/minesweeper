package com.stepanew.minesweeper.service;

import com.stepanew.minesweeper.domain.entity.Game;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.exception.CellIsOpenedException;
import com.stepanew.minesweeper.service.impl.GameStateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

class GameStateServiceImplTest {

    @InjectMocks
    private GameStateServiceImpl gameStateService;

    @Mock
    private FieldService fieldService;

    private Game game;

    private Cell[][] actualField;

    private Cell[][] visibleField;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameStateService = new GameStateServiceImpl(fieldService);

        game = new Game();
        game.setCompleted(false);

        actualField = new Cell[][]{
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
        };

        visibleField = new Cell[][]{
                {Cell.CELL_0, Cell.CELL_1, Cell.NOT_FOUNDED},
                {Cell.CELL_1, Cell.CELL_1, Cell.CELL_1},
                {Cell.NOT_FOUNDED, Cell.CELL_1, Cell.CELL_0}
        };
    }

    @Test
    void processTurn_WhenCellIsNotFounded_ShouldMarkGameAsCompletedAndRevealMines() {
        int row = 0;
        int column = 2;

        gameStateService.processTurn(game, actualField, visibleField, row, column);

        assertThat(game.isCompleted()).isTrue();
        verify(fieldService).revealAllMines(visibleField, actualField);
    }

    @Test
    void processTurn_WhenCellIsCell0_ShouldCallFloodFill() {
        int row = 0;
        int column = 0;

        gameStateService.processTurn(game, actualField, visibleField, row, column);

        verify(fieldService).floodFill(actualField, visibleField, row, column);
        assertThat(actualField[row][column]).isEqualTo(Cell.CELL_0);
    }

    @Test
    void processTurn_WhenCellIsOtherValue_ShouldUpdateActualField() {
        int row = 0;
        int column = 1;

        gameStateService.processTurn(game, actualField, visibleField, row, column);

        assertThat(actualField[row][column]).isEqualTo(Cell.CELL_1);
    }

    @Test
    void processTurn_WhenCellIsAlreadyOpened_ShouldThrowException() {
        actualField[0][0] = Cell.CELL_0;

        assertThrows(
                CellIsOpenedException.class,
                () -> gameStateService.processTurn(game, actualField, visibleField, 0, 0)
        );
    }

    @Test
    void isGameWon_WhenAllNonMineCellsAreOpened_ShouldReturnTrue() {
        Cell[][] actualField = {
                {Cell.CELL_0, Cell.CELL_1, Cell.EMPTY},
                {Cell.CELL_1, Cell.CELL_1, Cell.CELL_1},
                {Cell.EMPTY, Cell.CELL_1, Cell.CELL_0}
        };

        boolean result = gameStateService.isGameWon(actualField, visibleField);

        assertThat(result).isTrue();
    }

    @Test
    void isGameWon_WhenSomeNonMineCellsAreClosed_ShouldReturnFalse() {
        Cell[][] actualField = {
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
        };

        boolean result = gameStateService.isGameWon(actualField, visibleField);

        assertThat(result).isFalse();
    }

    @Test
    void markGameAsCompleted_ShouldSetCompletedToTrue() {
        gameStateService.markGameAsCompleted(game);

        assertThat(game.isCompleted()).isTrue();
    }
}