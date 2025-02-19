package com.stepanew.minesweeper.utils.strategy;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.enums.Cell;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Qualifier("hiddenStrategy")
public class HiddenFieldGenerationStrategy implements FieldGenerationStrategy {

    @Override
    public Cell[][] generateField(NewGameRequest request) {
        int height = request.height();
        int width = request.width();
        return generateHiddenField(width, height);
    }

    private Cell[][] generateHiddenField(int width, int height) {
        Cell[][] field = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(field[i], Cell.EMPTY);
        }
        return field;
    }

}