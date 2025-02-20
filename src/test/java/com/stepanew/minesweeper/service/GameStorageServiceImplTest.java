package com.stepanew.minesweeper.service;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.entity.Game;
import com.stepanew.minesweeper.domain.enums.Cell;
import com.stepanew.minesweeper.domain.mapper.GameMapper;
import com.stepanew.minesweeper.exception.GameIsNotFoundException;
import com.stepanew.minesweeper.repository.GameRepository;
import com.stepanew.minesweeper.service.impl.GameStorageServiceImpl;
import com.stepanew.minesweeper.utils.json.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameStorageServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GameStorageServiceImpl gameStorageService;

    private NewGameRequest request;
    private Cell[][] actualField;
    private Cell[][] visibleField;
    private Game game;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new NewGameRequest(3, 3, 2);

        actualField = new Cell[][]{
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
        };
        visibleField = new Cell[][]{
                {Cell.CELL_0, Cell.CELL_1, Cell.NOT_FOUNDED},
                {Cell.CELL_1, Cell.CELL_1, Cell.CELL_1},
                {Cell.NOT_FOUNDED, Cell.CELL_1, Cell.CELL_0}
        };

        gameId = UUID.randomUUID();
        game = new Game();
        game.setGameId(gameId);
        game.setActualField(JsonUtils.serialize(actualField));
        game.setVisibleField(JsonUtils.serialize(visibleField));
    }

    @Test
    void saveGame_ShouldSaveGameAndReturnIt() {
        when(gameMapper
                .toEntity(
                        request,
                        JsonUtils.serialize(actualField),
                        JsonUtils.serialize(visibleField)
                )
        ).thenReturn(game);
        when(gameRepository.save(game)).thenReturn(game);

        Game result = gameStorageService.saveGame(request, actualField, visibleField);

        assertThat(result).isEqualTo(game);
        verify(gameMapper).toEntity(
                request,
                JsonUtils.serialize(actualField),
                JsonUtils.serialize(visibleField)
        );
        verify(gameRepository).save(game);
    }

    @Test
    void loadGame_WhenGameExists_ShouldReturnGame() {
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        Game result = gameStorageService.loadGame(gameId);

        assertThat(result).isEqualTo(game);
        verify(gameRepository).findById(gameId);
    }

    @Test
    void loadGame_WhenGameDoesNotExist_ShouldThrowException() {
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(
                GameIsNotFoundException.class,
                () -> gameStorageService.loadGame(gameId)
        );
        verify(gameRepository).findById(gameId);
    }

    @Test
    void getActualField_ShouldDeserializeAndReturnField() {
        Cell[][] result = gameStorageService.getActualField(game);

        assertThat(result).isEqualTo(actualField);
    }

    @Test
    void getVisibleField_ShouldDeserializeAndReturnField() {
        Cell[][] result = gameStorageService.getVisibleField(game);

        assertThat(result).isEqualTo(visibleField);
    }

    @Test
    void updateGame_ShouldUpdateAndReturnGame() {
        when(gameRepository.save(game)).thenReturn(game);

        Game result = gameStorageService.updateGame(game, actualField);

        assertThat(result).isEqualTo(game);
        assertThat(result.getActualField()).isEqualTo(JsonUtils.serialize(actualField));
        verify(gameRepository).save(game);
    }
}