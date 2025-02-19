package com.stepanew.minesweeper.exception;

import java.util.UUID;

public class GameIsNotFoundException extends GameException {

    private static final String MESSAGE = "Игра с id %s не найдена";

    public GameIsNotFoundException(UUID gameId) {
        super(String.format(MESSAGE, gameId.toString()));
    }

}
