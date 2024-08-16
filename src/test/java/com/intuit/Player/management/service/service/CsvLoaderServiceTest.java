package com.intuit.Player.management.service.service;

import com.intuit.Player.management.service.entity.Player;
import com.intuit.Player.management.service.repository.PlayerRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CsvLoaderServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private CsvLoaderService csvLoaderService;

    @Mock
    private CsvToBeanBuilder<Player> csvToBeanBuilder;

    @Mock
    private CsvToBean<Player> csvToBean;

    private Reader reader;

    @BeforeEach
    public void setUp() throws Exception {
        reader = mock(FileReader.class);
//        when(csvToBeanBuilder.withType(Player.class)).thenReturn(csvToBeanBuilder);
//        when(csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true)).thenReturn(csvToBeanBuilder);
//        when(csvToBeanBuilder.build()).thenReturn(csvToBean);
    }

    @Test
    public void testLoadPlayersFromCsv_SuccessfulLoad() throws IOException {
        // Arrange
        when(playerRepository.count()).thenReturn(0L);
        List<Player> players = Collections.singletonList(new Player());
        when(csvToBean.parse()).thenReturn(players);

        // Act
        csvLoaderService.loadPlayersFromCsv();

        // Assert
        verify(playerRepository, times(1)).saveAll(players);
    }

    @Test
    public void testLoadPlayersFromCsv_NoLoadIfDataExists() throws IOException {
        // Arrange
        when(playerRepository.count()).thenReturn(10L);

        // Act
        csvLoaderService.loadPlayersFromCsv();

        // Assert
        verify(playerRepository, never()).saveAll(anyList());
    }

//    @Test
//    public void testLoadPlayersFromCsv_FileNotFound() {
//        // Arrange
//        when(playerRepository.count()).thenReturn(0L);
//        doThrow(IllegalStateException.class).when(csvToBean).parse();
//
//        // Act & Assert
//        assertThrows(IOException.class, () -> csvLoaderService.loadPlayersFromCsv());
//    }

    @Test
    public void testLoadPlayersFromCsv_EmptyCsvFile() throws IOException {
        // Arrange
        when(playerRepository.count()).thenReturn(0L);
        when(csvToBean.parse()).thenReturn(Collections.emptyList());

        // Act
        csvLoaderService.loadPlayersFromCsv();

        // Assert
        verify(playerRepository, never()).saveAll(anyList());
    }

    @Test
    public void testLoadPlayersFromCsv_CsvFileWithInvalidData() throws IOException {
        // Arrange
        when(playerRepository.count()).thenReturn(0L);

        List<Player> players = new ArrayList<>();
        players.add(new Player()); // Assume this player has invalid data (e.g., missing fields)
        when(csvToBean.parse()).thenReturn(players);

        // Act
        csvLoaderService.loadPlayersFromCsv();

        // Assert
        verify(playerRepository, times(1)).saveAll(players);
    }

    @Test
    public void testLoadPlayersFromCsv_CsvFileWithDuplicateEntries() throws IOException {
        // Arrange
        when(playerRepository.count()).thenReturn(0L);

        Player player = new Player(); // Create a mock player
        List<Player> players = List.of(player, player); // Duplicates
        when(csvToBean.parse()).thenReturn(players);

        // Act
        csvLoaderService.loadPlayersFromCsv();

        // Assert
        verify(playerRepository, times(1)).saveAll(players); // Adjust depending on duplicate handling strategy
    }

    @Test
    public void testLoadPlayersFromCsv_LargeCsvFile() throws IOException {
        // Arrange
        when(playerRepository.count()).thenReturn(0L);

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            players.add(new Player()); // Create a large number of mock players
        }
        when(csvToBean.parse()).thenReturn(players);

        // Act
        csvLoaderService.loadPlayersFromCsv();

        // Assert
        verify(playerRepository, times(1)).saveAll(players); // Ensure all are saved efficiently
    }

    @Test
    public void testLoadPlayersFromCsv_DatabaseSaveFailure() throws IOException {
        // Arrange
        when(playerRepository.count()).thenReturn(0L);

        List<Player> players = Collections.singletonList(new Player());
        when(csvToBean.parse()).thenReturn(players);
        doThrow(new RuntimeException("Database Error")).when(playerRepository).saveAll(players);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> csvLoaderService.loadPlayersFromCsv());
    }
}

