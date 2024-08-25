package com.intuit.Player.management.service.controller;

import com.intuit.Player.management.service.dto.PlayerRequestParams;
import com.intuit.Player.management.service.entity.Player;
import com.intuit.Player.management.service.exception.PlayerNotFoundException;
import com.intuit.Player.management.service.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing players.
 * Provides endpoints to fetch all players and retrieve a player by their ID.
 */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("api/players")
public class PlayerController {

    private final PlayerService playerService;

    /**
     * Retrieves a paginated list of players based on the provided request parameters.
     *
     * @param playerRequestParams Request parameters for pagination and sorting.
     * @return A paginated list of players.
     */
    @GetMapping
    public Page<Player> getAll(PlayerRequestParams playerRequestParams) {
        log.debug("Received request to get all players with parameters: {}", playerRequestParams);
        Sort sortBy = getSort(playerRequestParams.getSortProperty(), playerRequestParams.getSortDirection());
        Pageable pageable = PageRequest.of(playerRequestParams.getPage(), playerRequestParams.getSize(), sortBy);
        Page<Player> players = playerService.getAllPlayers(pageable);
        log.debug("Returning {} players", players.getTotalElements());
        return players;
    }

    /**
     * Retrieves a player by their ID.
     *
     * @param id The unique identifier of the player.
     * @return The player with the specified ID.
     * @throws PlayerNotFoundException If a player with the specified ID is not found.
     */
    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable String id) {
        log.debug("Received request to get player with ID: {}", id);
        return playerService.getPlayerById(id);
    }

    /**
     * Constructs a Sort object based on the provided property and direction.
     *
     * @param sortProperty The property to sort by.
     * @param sortDirection The direction to sort in (ascending or descending).
     * @return A Sort object configured with the specified property and direction.
     */
    private Sort getSort(String sortProperty, PlayerRequestParams.SortDirection sortDirection) {
        return Sort.by(sortDirection == PlayerRequestParams.SortDirection.ASC ? Sort.Order.asc(sortProperty) : Sort.Order.desc(sortProperty));
    }
}

