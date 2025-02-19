package com.stepanew.minesweeper.service.impl;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.service.FieldService;
import com.stepanew.minesweeper.utils.strategy.FieldGenerationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

}
