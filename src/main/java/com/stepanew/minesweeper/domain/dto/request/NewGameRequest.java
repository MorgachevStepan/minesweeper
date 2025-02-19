package com.stepanew.minesweeper.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NewGameRequest(

        @NotNull(message = "Ширина игрового поля не может быть null")
        @Min(value = 1, message = "Ширина игрового поля не может быть меньше 1")
        @Max(value = 30, message = "Ширина игрового поля не может быть больше 30")
        Integer width,

        @NotNull(message = "Высота игрового поля не может быть null")
        @Min(value = 1, message = "Высота игрового поля не может быть меньше 1")
        @Max(value = 30, message = "Высота игрового поля не может быть больше 30")
        Integer height,

        @JsonProperty("mines_count")
        @NotNull(message = "Количество мин не может быть null")
        @Min(value = 1, message = "Количество мин не может быть меньше 1")
        Integer minesCount

) {
}
