package com.stepanew.minesweeper.utils.strategy;

import com.stepanew.minesweeper.domain.dto.request.NewGameRequest;
import com.stepanew.minesweeper.domain.enums.Cell;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Реализация стратегии генерации стандартного игрового поля.
 * Заполняет поле пустыми клетками, расставляет мины случайным образом и вычисляет числа вокруг мин.
 */
@Component
@Qualifier("defaultStrategy")
@RequiredArgsConstructor
public class DefaultFieldGenerationStrategy implements FieldGenerationStrategy {

    private final Random random;

    /**
     * Генерирует игровое поле с минами и числами.
     */
    @Override
    public Cell[][] generateField(NewGameRequest request) {
        int height = request.height();
        int width = request.width();
        int minesCount = request.minesCount();

        Cell[][] field = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            Arrays.fill(field[y], Cell.EMPTY);
        }

        Set<Point> mines = placeMines(width, height, minesCount);

        fillNumbers(field, mines, width, height);

        return field;
    }

    /**
     * Расставляет мины случайным образом на поле.
     * Использует алгоритм тасования Фишера-Йетса (перемешивает все координаты и выбирает нужное количество).
     */
    private Set<Point> placeMines(int width, int height, int minesCount) {
        List<Point> allPoints = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                allPoints.add(new Point(x, y));
            }
        }

        for (int i = allPoints.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Point temp = allPoints.get(i);
            allPoints.set(i, allPoints.get(j));
            allPoints.set(j, temp);
        }

        return new HashSet<>(allPoints.subList(0, minesCount));
    }

    /**
     * Заполняет числа вокруг мин, увеличивая значения соседних клеток.
     * В цикле проходим по каждой мине и добавляем ее на поле.
     * Проходим по всем соседним для мины клеткам (двойной for).
     * В if проверяем, что соседняя клетка в пределах поля и не является миной.
     * Если все хорошо, что увеличиваем значение клетки
     */
    private void fillNumbers(Cell[][] field, Set<Point> mines, int width, int height) {
        for (Point mine : mines) {
            field[mine.y()][mine.x()] = Cell.NOT_FOUNDED;

            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    int nx = mine.x() + dx;
                    int ny = mine.y() + dy;

                    if (nx >= 0 && nx < width && ny >= 0 && ny < height && field[ny][nx] != Cell.NOT_FOUNDED) {
                        field[ny][nx] = getNextCell(field[ny][nx]);
                    }
                }
            }
        }
    }

    /**
     * Конечный автомат, который увеличивает значение клетки (из EMPTY в CELL_1, из CELL_1 в CELL_2 и так далее).
     * Если клетка уже имеет значение CELL_8, оно остается неизменным.
     */
    private Cell getNextCell(Cell cell) {
        return switch (cell) {
            case EMPTY -> Cell.CELL_1;
            case CELL_1 -> Cell.CELL_2;
            case CELL_2 -> Cell.CELL_3;
            case CELL_3 -> Cell.CELL_4;
            case CELL_4 -> Cell.CELL_5;
            case CELL_5 -> Cell.CELL_6;
            case CELL_6 -> Cell.CELL_7;
            case CELL_7 -> Cell.CELL_8;
            default -> cell;
        };
    }

    /**
     * Вспомогательный класс для хранения координат точки на поле.
     */
    private record Point(int x, int y) {}
}
