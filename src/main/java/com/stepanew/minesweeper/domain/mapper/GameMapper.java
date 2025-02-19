package com.stepanew.minesweeper.domain.mapper;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.dto.response.GameInfoResponse;
import com.stepanew.minesweeper.domain.entity.Game;
import com.stepanew.minesweeper.domain.enums.Cell;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameMapper {

    public Game toEntity(NewGameRequest request, String actualFieldJson, String visibleFieldJson) {
        Game game = new Game();
        game.setWidth(request.width());
        game.setHeight(request.height());
        game.setMinesCount(request.minesCount());
        game.setActualField(actualFieldJson);
        game.setVisibleField(visibleFieldJson);
        return game;
    }

    public GameInfoResponse toResponse(Game game, Cell[][] actualField) {
        List<List<String>> fieldResponse = Arrays.stream(actualField)
                .map(row -> Arrays.stream(row)
                        .map(Cell::toString)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        return new GameInfoResponse(
                game.getGameId(),
                game.getWidth(),
                game.getHeight(),
                game.getMinesCount(),
                game.isCompleted(),
                fieldResponse
        );
    }

}
