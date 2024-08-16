package com.intuit.Player.management.service.service;

import com.intuit.Player.management.service.entity.Player;
import com.intuit.Player.management.service.exception.PlayerNotFoundException;
import com.intuit.Player.management.service.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    public static final String PLAYER_ID = "playerID";

    public Page<Player> getAllPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    public Player getPlayerById(String id) {
        return playerRepository.
                findById(id).
                orElseThrow(() -> new PlayerNotFoundException(id));
    }
}
