package com.stepanew.minesweeper.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "game")
@NoArgsConstructor
@Setter
@Getter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "game_id", nullable = false, updatable = false)
    private UUID gameId;

    @Column(name = "width", nullable = false)
    private int width;

    @Column(name = "height", nullable = false)
    private int height;

    @Column(name = "mines_count", nullable = false)
    private int minesCount;

    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "actual_field", columnDefinition = "jsonb", nullable = false)
    private String actualField;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "visible_field", columnDefinition = "jsonb", nullable = false)
    private String visibleField;

}
