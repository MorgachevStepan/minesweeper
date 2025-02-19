package com.stepanew.minesweeper.exception;

public class SerializationException extends GameException {

    private static final String MESSAGE = "Ошибка сериализации игрового поля";

    public SerializationException() {
        super(MESSAGE);
    }

}
