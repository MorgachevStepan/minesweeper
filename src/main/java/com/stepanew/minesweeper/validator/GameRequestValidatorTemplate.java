package com.stepanew.minesweeper.validator;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;

public abstract class GameRequestValidatorTemplate {

    public final void validate(NewGameRequest request) {
        validateMinesCount(request);
    }

    protected abstract void validateMinesCount(NewGameRequest request);

}
