package com.stepanew.minesweeper.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GameTurnRequest(

        @JsonProperty("game_id")
        @NotNull(message = "Game_id не должен быть null")
        UUID gameId,

        @JsonProperty("col")
        @NotNull(message = "Номер колонки не может быть null")
        @Min(value = 0, message = "Номер колонки не может быть меньше 0")
        @Max(value = 29, message = "Номер колонки не может быть больше 29")
        Integer column,

        @NotNull(message = "Номер ряда не может быть null")
        @Min(value = 0, message = "Номер ряда не может быть меньше 0")
        @Max(value = 29, message = "Номер ряда не может быть больше 29")
        Integer row

) {
}
