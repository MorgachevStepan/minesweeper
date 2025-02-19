package com.stepanew.minesweeper.utils.strategy;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.enums.Cell;

public interface FieldGenerationStrategy {

    Cell[][] generateField(NewGameRequest request);

}
