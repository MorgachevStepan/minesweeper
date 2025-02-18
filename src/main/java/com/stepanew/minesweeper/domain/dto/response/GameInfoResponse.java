package com.stepanew.minesweeper.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stepanew.minesweeper.domain.enums.Cell;

import java.util.List;
import java.util.UUID;

public record GameInfoResponse(

        @JsonProperty("game_id")
        UUID gameId,

        Integer width,

        Integer height,

        @JsonProperty("mines_count")
        Integer minesCount,

        Boolean completed,

        List<List<Cell>> field

) {
}
