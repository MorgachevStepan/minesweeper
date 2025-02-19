package com.stepanew.minesweeper.service.impl;

import com.stepanew.minesweeper.domain.dto.request.GameTurnRequest;
import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.dto.response.GameInfoResponse;
import com.stepanew.minesweeper.domain.entity.Game;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.domain.mapper.GameMapper;
import com.stepanew.minesweeper.exception.CellIsOpenedException;
import com.stepanew.minesweeper.exception.GameIsCompletedException;
import com.stepanew.minesweeper.exception.GameIsNotFoundException;
import com.stepanew.minesweeper.repository.GameRepository;
import com.stepanew.minesweeper.service.FieldService;
import com.stepanew.minesweeper.service.GameService;
import com.stepanew.minesweeper.utils.json.JsonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Stack;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    private final FieldService fieldService;

    @Override
    @Transactional
    public GameInfoResponse createGame(NewGameRequest request) {
        Cell[][] actualField = fieldService.generateActualField(request);
        Cell[][] visibleField = fieldService.generateVisibleField(request);

        String actualFieldJson = JsonUtils.serialize(actualField);
        String visibleFieldJson = JsonUtils.serialize(visibleField);

        Game game = gameMapper.toEntity(request, actualFieldJson, visibleFieldJson);
        gameRepository.save(game);

        return gameMapper.toResponse(game, actualField);
    }

    @Override
    @Transactional
    public GameInfoResponse makeNewTurn(GameTurnRequest request) {
        Game game = getGameById(request.gameId());

        if (game.isCompleted()) {
            throw new GameIsCompletedException();
        }

        Cell[][] actualField = JsonUtils.deserialize(game.getActualField(), Cell[][].class);
        Cell[][] visibleField = JsonUtils.deserialize(game.getVisibleField(), Cell[][].class);

        for (Cell[] cells: visibleField) {
            for (Cell cell: cells) {
                System.out.print(cell.name());
            }
            System.out.println();
        }

        int row = request.row();
        int column = request.column();

        if (actualField[row][column] != Cell.EMPTY) {
            throw new CellIsOpenedException();
        }

        Cell selectedCell = visibleField[row][column];

        if (selectedCell == Cell.NOT_FOUNDED) {
            game.setCompleted(true);
            revealAllMines(visibleField, actualField);
        } else if (selectedCell == Cell.CELL_0) {
            floodFill(actualField, visibleField, row, column);
            actualField[row][column] = selectedCell;
        } else {
            actualField[row][column] = selectedCell;
        }

        if (isGameWon(actualField, visibleField)) {
            game.setCompleted(true);
            revealAllFounded(actualField);
        }

        game.setActualField(JsonUtils.serialize(actualField));
        gameRepository.save(game);

        return gameMapper.toResponse(game, actualField);
    }

    private void revealAllFounded(Cell[][] actualField) {
        for (int y = 0; y < actualField.length; y++) {
            for (int x = 0; x < actualField[0].length; x++) {
                if (actualField[y][x] == Cell.EMPTY) {
                    actualField[y][x] = Cell.FOUNDED;
                }
            }
        }
    }

    private void floodFill(Cell[][] actualField, Cell[][] visibleField, int startRow, int startCol) {
        int height = actualField.length;
        int width = actualField[0].length;

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startRow, startCol});

        boolean[][] visited = new boolean[height][width];

        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            int row = cell[0];
            int col = cell[1];

            if (row < 0 || row >= height || col < 0 || col >= width) continue;
            if (visited[row][col]) continue;
            if (actualField[row][col] != Cell.EMPTY) continue;

            visited[row][col] = true;
            actualField[row][col] = visibleField[row][col];

            if (visibleField[row][col] == Cell.CELL_0) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        if (dx == 0 && dy == 0) continue;
                        stack.push(new int[]{row + dy, col + dx});
                    }
                }
            }
        }
    }

    private void revealAllMines(Cell[][] visibleField, Cell[][] actualField) {
        for (int y = 0; y < visibleField.length; y++) {
            System.arraycopy(visibleField[y], 0, actualField[y], 0, visibleField[0].length);
        }
    }

    private boolean isGameWon(Cell[][] actualField, Cell[][] visibleField) {
        for (int y = 0; y < actualField.length; y++) {
            for (int x = 0; x < actualField[0].length; x++) {
                if (visibleField[y][x] != Cell.NOT_FOUNDED && actualField[y][x] == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private Game getGameById(UUID gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameIsNotFoundException(gameId));
    }

}

