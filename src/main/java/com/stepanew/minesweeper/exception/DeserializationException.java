package com.stepanew.minesweeper.exception;

public class DeserializationException extends GameException {

    private static final String MESSAGE = "Ошибка десериализации игрового поля";

    public DeserializationException() {
        super(MESSAGE);
    }

}
