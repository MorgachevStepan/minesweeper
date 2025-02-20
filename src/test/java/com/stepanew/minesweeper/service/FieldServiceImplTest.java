package com.stepanew.minesweeper.service;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.service.impl.FieldServiceImpl;
import com.stepanew.minesweeper.utils.strategy.FieldGenerationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FieldServiceImplTest {

    @Mock
    private FieldGenerationStrategy defaultStrategy;

    @Mock
    private FieldGenerationStrategy hiddenStrategy;

    @InjectMocks
    private FieldServiceImpl fieldService;

    private NewGameRequest request;
    private Cell[][] actualField;
    private Cell[][] visibleField;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new NewGameRequest(3, 3, 2);

        visibleField = new Cell[][]{
                {Cell.CELL_0, Cell.CELL_1, Cell.NOT_FOUNDED},
                {Cell.CELL_1, Cell.CELL_1, Cell.CELL_1},
                {Cell.NOT_FOUNDED, Cell.CELL_1, Cell.CELL_0}
        };

        actualField = new Cell[][]{
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
        };

        when(hiddenStrategy.generateField(request)).thenReturn(actualField);
        when(defaultStrategy.generateField(request)).thenReturn(visibleField);
    }

    @Test
    void testGenerateActualField() {
        Cell[][] result = fieldService.generateActualField(request);

        assertThat(result).isEqualTo(actualField);
        verify(hiddenStrategy, times(1)).generateField(request);
    }

    @Test
    void testGenerateVisibleField() {
        Cell[][] result = fieldService.generateVisibleField(request);

        assertThat(result).isEqualTo(visibleField);
        verify(defaultStrategy, times(1)).generateField(request);
    }

    @Test
    void testFloodFill() {
        Cell[][] actualFieldCopy = deepCopy(actualField);
        Cell[][] visibleFieldCopy = deepCopy(visibleField);

        fieldService.floodFill(actualFieldCopy, visibleFieldCopy, 0, 0);

        assertThat(actualFieldCopy[0][0]).isEqualTo(Cell.CELL_0);
        assertThat(actualFieldCopy[0][1]).isEqualTo(Cell.CELL_1);
        assertThat(actualFieldCopy[1][0]).isEqualTo(Cell.CELL_1);
        assertThat(actualFieldCopy[1][1]).isEqualTo(Cell.CELL_1);
    }

    @Test
    void testRevealAllMines() {
        Cell[][] actualFieldCopy = deepCopy(actualField);
        Cell[][] visibleFieldCopy = deepCopy(visibleField);

        fieldService.revealAllMines(visibleFieldCopy, actualFieldCopy);

        assertThat(actualFieldCopy).isEqualTo(visibleFieldCopy);
    }

    @Test
    void testRevealAllFounded() {
        Cell[][] actualFieldCopy = deepCopy(actualField);

        fieldService.revealAllFounded(actualFieldCopy);

        for (Cell[] row : actualFieldCopy) {
            for (Cell cell : row) {
                if (cell == Cell.EMPTY) {
                    assertThat(cell).isEqualTo(Cell.FOUNDED);
                }
            }
        }
    }

    private Cell[][] deepCopy(Cell[][] original) {
        Cell[][] copy = new Cell[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

}