package com.stepanew.minesweeper.exception;

public class GameIsCompletedException extends GameException {

    private static final String MESSAGE = "Игра завершена";

    public GameIsCompletedException() {
        super(MESSAGE);
    }

}
