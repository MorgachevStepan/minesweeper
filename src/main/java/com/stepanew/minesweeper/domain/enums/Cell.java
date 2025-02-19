package com.stepanew.minesweeper.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static Cell fromValue(String value) {
        for (Cell cell : values()) {
            if (cell.value.equals(value)) {
                return cell;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }

}

