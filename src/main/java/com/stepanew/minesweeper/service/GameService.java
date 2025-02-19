package com.stepanew.minesweeper.service;

import com.stepanew.minesweeper.domain.dto.request.GameTurnRequest;
import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.dto.response.GameInfoResponse;

public interface GameService {

    GameInfoResponse createGame(NewGameRequest request);

    GameInfoResponse makeNewTurn(GameTurnRequest request);

}
