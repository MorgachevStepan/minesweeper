package com.stepanew.minesweeper.strategy;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.utils.strategy.HiddenFieldGenerationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HiddenFieldGenerationStrategyTest {

    private HiddenFieldGenerationStrategy fieldGenerationStrategy;

    @BeforeEach
    void setUp() {
        fieldGenerationStrategy = new HiddenFieldGenerationStrategy();
    }

    @Test
    void testGenerateField_CorrectDimensions() {
        NewGameRequest request = new NewGameRequest(5, 6, 3);

        Cell[][] field = fieldGenerationStrategy.generateField(request);

        assertEquals(6, field.length, "Высота поля должна быть 6");
        assertEquals(5, field[0].length, "Ширина поля должна быть 5");
    }

    @Test
    void testGenerateField_AllCellsAreEmpty() {
        NewGameRequest request = new NewGameRequest(4, 4, 2);

        Cell[][] field = fieldGenerationStrategy.generateField(request);

        for (Cell[] row : field) {
            for (Cell cell : row) {
                assertEquals(Cell.EMPTY, cell, "Все клетки должны быть пустыми");
            }
        }
    }
}
