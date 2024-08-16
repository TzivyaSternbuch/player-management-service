package com.intuit.Player.management.service.controller;

import com.intuit.Player.management.service.dto.PlayerRequestParams;
import com.intuit.Player.management.service.entity.Player;
import com.intuit.Player.management.service.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/players")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public Page<Player> getAll(PlayerRequestParams playerRequestParams) {
        Sort sortBy = getSort(playerRequestParams.getSortProperty(), playerRequestParams.getSortDirection());
        Pageable pageable = PageRequest.of(playerRequestParams.getPage(), playerRequestParams.getSize(), sortBy);
        return playerService.getAllPlayers(pageable);
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable String id) {
        return playerService.getPlayerById(id);
    }

    private Sort getSort(String sortProperty, PlayerRequestParams.SortDirection sortDirection) {
        return Sort.by(sortDirection == PlayerRequestParams.SortDirection.ASC ? Sort.Order.asc(sortProperty) : Sort.Order.desc(sortProperty));
    }
}

