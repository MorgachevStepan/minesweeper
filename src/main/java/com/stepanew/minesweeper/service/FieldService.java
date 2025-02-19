package com.stepanew.minesweeper.service;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.enums.Cell;

public interface FieldService {

    Cell[][] generateActualField(NewGameRequest request);

    Cell[][] generateVisibleField(NewGameRequest request);

}
