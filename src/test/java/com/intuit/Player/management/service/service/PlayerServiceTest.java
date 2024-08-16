package com.intuit.Player.management.service.service;

import com.intuit.Player.management.service.entity.Player;
import com.intuit.Player.management.service.exception.PlayerNotFoundException;
import com.intuit.Player.management.service.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void getAllPlayers_WhenOneFullPage_ReturnsSinglePage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Player player = new Player(); // Create a player object as per your entity structure
        Page<Player> playerPage = new PageImpl<>(Collections.singletonList(player));
        when(playerRepository.findAll(pageable)).thenReturn(playerPage);

        // Act
        Page<Player> result = playerService.getAllPlayers(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(playerRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAllPlayers_ReturnsEmptyPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Player> emptyPage = new PageImpl<>(Collections.emptyList());
        when(playerRepository.findAll(pageable)).thenReturn(emptyPage);

        // Act
        Page<Player> result = playerService.getAllPlayers(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        verify(playerRepository, times(1)).findAll(pageable);
    }

    @Test
    void getPlayerById_WithValidId_ReturnsPlayer() {
        // Arrange
        String playerId = "validId";
        Player expectedPlayer = new Player();
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(expectedPlayer));

        // Act
        Player result = playerService.getPlayerById(playerId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPlayer, result);
        verify(playerRepository, times(1)).findById(playerId);
    }



    @Test
    void getPlayerById_WithValidIdAndSpecialCharacters_ReturnsPlayer() {
        // Arrange
        String playerId = "' OR 1=1 --";
        Player player = new Player();
        player.setPlayerID(playerId);
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        // Act
        Player result = playerService.getPlayerById(playerId);

        // Assert
        assertNotNull(result);
        assertEquals(player, result);
        verify(playerRepository, times(1)).findById(playerId);
    }

    @Test
    void getPlayerById_WithSpecialCharacterId_ThrowsPlayerNotFoundException() {
        // Arrange
        String playerId = "' OR 1=1 --";
        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PlayerNotFoundException.class, () -> playerService.getPlayerById(playerId));
        verify(playerRepository, times(1)).findById(playerId);
    }

}

