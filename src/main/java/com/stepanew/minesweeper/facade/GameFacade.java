package com.stepanew.minesweeper.facade;

import com.stepanew.minesweeper.domain.dto.request.GameTurnRequest;
import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.dto.response.GameInfoResponse;

public interface GameFacade {

    GameInfoResponse createGame(NewGameRequest request);

    GameInfoResponse makeNewTurn(GameTurnRequest request);

}
