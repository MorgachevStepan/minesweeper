package com.stepanew.minesweeper.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Cell {

    EMPTY(" "),

    CELL_0("0"),

    CELL_1("1"),

    CELL_2("2"),

    CELL_3("3"),

    CELL_4("4"),

    CELL_5("5"),

    CELL_6("6"),

    CELL_7("7"),

    CELL_8("8"),

    NOT_FOUNDED("X"),

    FOUNDED("M");

    private final String value;

    @Override
    public String toString() {
        return value;
    }

}
