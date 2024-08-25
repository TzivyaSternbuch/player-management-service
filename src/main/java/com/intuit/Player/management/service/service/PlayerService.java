package com.intuit.Player.management.service.service;

import com.intuit.Player.management.service.entity.Player;
import com.intuit.Player.management.service.exception.PlayerNotFoundException;
import com.intuit.Player.management.service.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

/**
 * Service class for managing player data.
 * Provides methods to retrieve all players and get a player by their ID.
 */
@RequiredArgsConstructor
@Service
@Log4j2
public class PlayerService {

    private final PlayerRepository playerRepository;
    public static final String PLAYER_ID = "playerID";

    /**
     * Retrieves a paginated list of all players.
     *
     * @param pageable Pagination and sorting information.
     * @return A paginated list of players.
     */
    public Page<Player> getAllPlayers(Pageable pageable) {
        log.debug("Fetching all players with pagination: {}", pageable);
        return playerRepository.findAll(pageable);
    }

    /**
     * Retrieves a player by their unique ID.
     *
     * @param id The unique identifier of the player.
     * @return The player with the specified ID.
     * @throws PlayerNotFoundException If a player with the specified ID is not found.
     */
    public Player getPlayerById(String id) {
        log.debug("Fetching player with ID: {}", id);
        return playerRepository.
                findById(id).
                orElseThrow(() -> new PlayerNotFoundException(id));
    }
}
