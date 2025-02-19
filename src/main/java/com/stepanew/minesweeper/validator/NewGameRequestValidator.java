package com.stepanew.minesweeper.validator;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.exception.MinesCountIsIllegalException;
import org.springframework.stereotype.Component;

@Component
public class NewGameRequestValidator extends GameRequestValidatorTemplate {

    @Override
    protected void validateMinesCount(NewGameRequest request) {
        int constraint = request.height() * request.width() - 1;
        if (request.minesCount() > constraint) {
            throw new MinesCountIsIllegalException(constraint);
        }
    }

}
