package com.stepanew.minesweeper.repository;

import com.stepanew.minesweeper.domain.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
}
