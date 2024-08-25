package com.intuit.Player.management.service.repository;

import com.intuit.Player.management.service.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing player entities.
 * Provides CRUD operations for player data.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
}
