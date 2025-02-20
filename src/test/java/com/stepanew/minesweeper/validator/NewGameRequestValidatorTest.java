package com.stepanew.minesweeper.validator;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.exception.MinesCountIsIllegalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewGameRequestValidatorTest {

    private NewGameRequestValidator validator;

    @BeforeEach
    void setUp() {
        validator = new NewGameRequestValidator();
    }

    @Test
    void testValidateMinesCount_ValidMines() {
        NewGameRequest request = new NewGameRequest(5, 5, 10);

        assertDoesNotThrow(() -> validator.validateMinesCount(request));
    }

    @Test
    void testValidateMinesCount_TooManyMines() {
        NewGameRequest request = new NewGameRequest(4, 4, 16);

        assertThrows(
                MinesCountIsIllegalException.class,
                () -> validator.validateMinesCount(request)
        );
    }
}
