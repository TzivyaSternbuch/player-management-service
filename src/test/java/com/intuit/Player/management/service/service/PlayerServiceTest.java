package com.intuit.Player.management.service.service;

import com.intuit.Player.management.service.entity.Player;
import com.intuit.Player.management.service.exception.PlayerNotFoundException;
import com.intuit.Player.management.service.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

//    public PlayerServiceTest() {
//        MockitoAnnotations.openMocks(this);
//    }

//    @Test
//    public void testGetPlayerById_PlayerByIdExists() {
//        String playerId = "adf4";
//        Player player = new Player();
//        player.setPlayerID(playerId);
//        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
//
//        Player foundPlayer = playerService.getPlayerById(playerId);
//
//        assertNotNull(foundPlayer);
//        assertEquals(playerId, foundPlayer.getPlayerID());
//    }

    @Test
    void testGetPlayerById_PlayerDoesNotExist() {
        // Arrange
        String playerId = "as123";
        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(PlayerNotFoundException.class, () -> {
            playerService.getPlayerById(playerId);
        });
        assertEquals("Player with id as123 not found", exception.getMessage());
    }
}

