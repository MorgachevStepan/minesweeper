package com.stepanew.minesweeper.exception;

public class MinesCountIsIllegalException extends GameException {

    private static final String MESSAGE = "Количество мин должно быть не менее 1 и не более %d";

    public MinesCountIsIllegalException(int count) {
        super(String.format(MESSAGE, count));
    }

}
