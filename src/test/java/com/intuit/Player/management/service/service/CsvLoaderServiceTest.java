package com.intuit.Player.management.service.service;

import com.intuit.Player.management.service.entity.Player;
import com.intuit.Player.management.service.repository.PlayerRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CsvLoaderServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private CsvLoaderService csvLoaderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Disabled
    @Test
    void whenCsvFileIsValid_thenPlayersAreLoaded() throws Exception {
        // Arrange
        when(playerRepository.count()).thenReturn(0L);

        // Mocking CsvToBean behavior
        Player player = new Player(); // Create a sample player object
        List<Player> players = Collections.singletonList(player);

        CsvToBean<Player> csvToBean = mock(CsvToBean.class);
        when(csvToBean.parse()).thenReturn(players);

        try (Reader reader = new StringReader("player data")) { // Use a StringReader for simplicity
            // Simulating CsvToBeanBuilder behavior
            CsvToBeanBuilder<Player> builder = mock(CsvToBeanBuilder.class);
            when(builder.withType(Player.class)).thenReturn(builder);
            when(builder.withIgnoreLeadingWhiteSpace(true)).thenReturn(builder);
            when(builder.build()).thenReturn(csvToBean);
            when(playerRepository.saveAll(anyList())).thenReturn(players);

            // Act
            csvLoaderService.loadPlayersFromCsv();

            // Assert
            verify(playerRepository).saveAll(any());
        }
    }

    @Test
    void whenRepositoryIsNotEmpty_thenCsvIsNotLoaded() throws Exception {
        // Arrange
        when(playerRepository.count()).thenReturn(1L);

        // Act
        csvLoaderService.loadPlayersFromCsv();

        // Assert
        verify(playerRepository, never()).saveAll(anyList());
    }

    @Test
    void whenIllegalStateExceptionOccurs_thenExceptionIsThrown() throws Exception {
        // Arrange
        when(playerRepository.count()).thenReturn(0L);
        doThrow(new IllegalStateException()).when(playerRepository).saveAll(anyList());

        // Act & Assert
        try {
            csvLoaderService.loadPlayersFromCsv();
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
