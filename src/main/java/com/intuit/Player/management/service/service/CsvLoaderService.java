package com.intuit.Player.management.service.service;


import com.intuit.Player.management.service.entity.Player;
import com.intuit.Player.management.service.repository.PlayerRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Service class responsible for loading player data from a CSV file into the database.
 * This service ensures data is loaded only once during application startup.
 */
@RequiredArgsConstructor
@Service
@Log4j2
public class CsvLoaderService {

    private final PlayerRepository playerRepository;
    private final String CSV_PATH = "src/main/resources/static/player.csv";

    /**
     * Loads player data from a CSV file into the database.
     * This method is called automatically once during application startup.
     * The data is loaded only if the database is initially empty.
     *
     * @throws IOException If there is an issue reading the CSV file.
     */
    @PostConstruct
    public void loadPlayersFromCsv() throws IOException {
        if (playerRepository.count() == 0) { // Ensure data is loaded only once
            try (Reader reader = new FileReader(CSV_PATH)) {
                CsvToBean<Player> csvToBean = new CsvToBeanBuilder<Player>(reader)
                        .withType(Player.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                List<Player> players = csvToBean.parse();
                playerRepository.saveAll(players);
                log.info("Successfully loaded and saved {} players from CSV", players.size());
            } catch (IOException | IllegalStateException e) {
                log.error("Failed to load players from CSV file. Ensure the file exists and is accessible", e);
                throw e;
            }
        }
    }
}

