package com.stepanew.minesweeper.exception;

public class CellIsOpenedException extends GameException {

    private static final String MESSAGE = "Ячейка уже открыта";

    public CellIsOpenedException() {
        super(MESSAGE);
    }

}
