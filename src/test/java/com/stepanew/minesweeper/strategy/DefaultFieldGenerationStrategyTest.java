package com.stepanew.minesweeper.strategy;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.utils.strategy.DefaultFieldGenerationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultFieldGenerationStrategyTest {

    private DefaultFieldGenerationStrategy fieldGenerationStrategy;

    @BeforeEach
    void setUp() {
        Random randomMock = Mockito.mock(Random.class);
        fieldGenerationStrategy = new DefaultFieldGenerationStrategy(randomMock);
    }

    @Test
    void testGenerateField_CorrectMineCount() {
        NewGameRequest request = new NewGameRequest(4, 4, 3);

        Cell[][] field = fieldGenerationStrategy.generateField(request);

        int mineCount = countMines(field);
        int width = field.length;
        int height = field[0].length;
        assertEquals(request.minesCount(), mineCount, "На поле должно быть ровно 3 мины");
        assertEquals(request.width(), width, "Ширина поля должна равняться 4");
        assertEquals(request.height(), height, "Ширина поля должна равняться 4");
    }

    private int countMines(Cell[][] field) {
        return (int)java.util.Arrays.stream(field)
                .flatMap(java.util.Arrays::stream)
                .filter(cell -> cell == Cell.NOT_FOUNDED)
                .count();
    }
}
