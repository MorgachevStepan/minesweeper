package com.stepanew.minesweeper.facade.impl;

import com.stepanew.minesweeper.domain.dto.request.GameTurnRequest;
import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.dto.response.GameInfoResponse;
import com.stepanew.minesweeper.facade.GameFacade;
import com.stepanew.minesweeper.service.GameService;
import com.stepanew.minesweeper.validator.GameRequestValidatorTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameFacadeImpl implements GameFacade {

    private final GameService gameService;

    private final GameRequestValidatorTemplate newGameRequestValidator;

    @Override
    public GameInfoResponse createGame(NewGameRequest request) {
        newGameRequestValidator.validate(request);
        return gameService.createGame(request);
    }

    @Override
    public GameInfoResponse makeNewTurn(GameTurnRequest request) {
        return gameService.makeNewTurn(request);
    }

}
