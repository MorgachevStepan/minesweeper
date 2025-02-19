package com.stepanew.minesweeper.service.impl;

import com.stepanew.minesweeper.domain.dto.request.GameTurnRequest;
import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.dto.response.GameInfoResponse;
import com.stepanew.minesweeper.domain.entity.Game;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.domain.mapper.GameMapper;
import com.stepanew.minesweeper.exception.GameIsCompletedException;
import com.stepanew.minesweeper.service.FieldService;
import com.stepanew.minesweeper.service.GameService;
import com.stepanew.minesweeper.service.GameStateService;
import com.stepanew.minesweeper.service.GameStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameMapper gameMapper;

    private final FieldService fieldService;

    private final GameStateService gameStateService;

    private final GameStorageService gameStorageService;

    @Override
    @Transactional
    public GameInfoResponse createGame(NewGameRequest request) {
        Cell[][] actualField = fieldService.generateActualField(request);
        Cell[][] visibleField = fieldService.generateVisibleField(request);

        Game game = gameStorageService.saveGame(request, actualField, visibleField);

        return gameMapper.toResponse(game, actualField);
    }

    @Override
    @Transactional
    public GameInfoResponse makeNewTurn(GameTurnRequest request) {
        Game game = gameStorageService.loadGame(request.gameId());

        if (game.isCompleted()) {
            throw new GameIsCompletedException();
        }

        Cell[][] actualField = gameStorageService.getActualField(game);
        Cell[][] visibleField = gameStorageService.getVisibleField(game);

        gameStateService.processTurn(game, actualField, visibleField, request.row(), request.column());

        if (gameStateService.isGameWon(actualField, visibleField)) {
            gameStateService.markGameAsCompleted(game);
            fieldService.revealAllFounded(actualField);
        }

        gameStorageService.updateGame(game, actualField);

        return gameMapper.toResponse(game, actualField);
    }

}

