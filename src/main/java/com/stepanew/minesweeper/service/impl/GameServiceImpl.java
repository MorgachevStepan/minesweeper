package com.stepanew.minesweeper.service.impl;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.dto.response.GameInfoResponse;
import com.stepanew.minesweeper.domain.entity.Game;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.domain.mapper.GameMapper;
import com.stepanew.minesweeper.repository.GameRepository;
import com.stepanew.minesweeper.service.FieldService;
import com.stepanew.minesweeper.service.GameService;
import com.stepanew.minesweeper.utils.json.JsonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    private final FieldService fieldService;

    @Override
    public GameInfoResponse createGame(NewGameRequest request) {
        Cell[][] actualField = fieldService.generateActualField(request);
        Cell[][] visibleField = fieldService.generateVisibleField(request);

        String actualFieldJson = JsonUtils.serialize(actualField);
        String visibleFieldJson = JsonUtils.serialize(visibleField);

        Game game = gameMapper.toEntity(request, actualFieldJson, visibleFieldJson);
        gameRepository.save(game);

        return gameMapper.toResponse(game, actualField);
    }

}

