package com.stepanew.minesweeper.service.impl;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.service.FieldService;
import com.stepanew.minesweeper.utils.strategy.FieldGenerationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class FieldServiceImpl implements FieldService {

    private final FieldGenerationStrategy defaultStrategy;
    private final FieldGenerationStrategy hiddenStrategy;

    @Autowired
    public FieldServiceImpl(
            @Qualifier("defaultStrategy") FieldGenerationStrategy defaultStrategy,
            @Qualifier("hiddenStrategy") FieldGenerationStrategy hiddenStrategy
    ) {
        this.defaultStrategy = defaultStrategy;
        this.hiddenStrategy = hiddenStrategy;
    }

    public Cell[][] generateActualField(NewGameRequest request) {
        return hiddenStrategy.generateField(request);
    }

    public Cell[][] generateVisibleField(NewGameRequest request) {
        return defaultStrategy.generateField(request);
    }

    public void floodFill(Cell[][] actualField, Cell[][] visibleField, int startRow, int startCol) {
        int height = actualField.length;
        int width = actualField[0].length;

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startRow, startCol});

        boolean[][] visited = new boolean[height][width];

        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            int row = cell[0];
            int col = cell[1];

            if (row < 0 || row >= height || col < 0 || col >= width) continue;
            if (visited[row][col]) continue;
            if (actualField[row][col] != Cell.EMPTY) continue;

            visited[row][col] = true;
            actualField[row][col] = visibleField[row][col];

            if (visibleField[row][col] == Cell.CELL_0) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        if (dx == 0 && dy == 0) continue;
                        stack.push(new int[]{row + dy, col + dx});
                    }
                }
            }
        }
    }

    public void revealAllMines(Cell[][] visibleField, Cell[][] actualField) {
        for (int y = 0; y < visibleField.length; y++) {
            System.arraycopy(visibleField[y], 0, actualField[y], 0, visibleField[0].length);
        }
    }

    public void revealAllFounded(Cell[][] actualField) {
        for (int y = 0; y < actualField.length; y++) {
            for (int x = 0; x < actualField[0].length; x++) {
                if (actualField[y][x] == Cell.EMPTY) {
                    actualField[y][x] = Cell.FOUNDED;
                }
            }
        }
    }

}
