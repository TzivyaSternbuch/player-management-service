package com.intuit.Player.management.service.repository;

import com.intuit.Player.management.service.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    public void setUp() {
        // Clear the repository before each test
        playerRepository.deleteAll();

        // Add some test data
        Player player1 = new Player();
        player1.setPlayerID("1a");
        Player player2 = new Player();
        player2.setPlayerID("qwde");
        playerRepository.save(player1);
        playerRepository.save(player2);
    }

    @Test
    public void findAllWithPagination_whenPlayersExist_ReturnPagedPlayers() {
        // Execute the method to test with pagination
        Pageable pageable = PageRequest.of(0, 1);
        Page<Player> playerPage = playerRepository.findAll(pageable);

        // Assert the results
        assertThat(playerPage).hasSize(1);
        assertThat(playerPage.getTotalElements()).isEqualTo(2);
        assertThat(playerPage.getTotalPages()).isEqualTo(2);
    }

    @Test
    public void findAll_whenNoPlayersExist_ReturnEmptyList() {
        // Clear the repository
        playerRepository.deleteAll();

        // Execute the method to test
        var players = playerRepository.findAll();

        // Assert the results
        assertThat(players).isEmpty();
    }

    @Test
    public void findAll_whenNoPlayersExist_ReturnEmptyPage() {
        // Clear the repository
        playerRepository.deleteAll();

        // Execute the method to test with pagination
        Pageable pageable = PageRequest.of(0, 1); // Page 0 with 1 item per page
        Page<Player> playerPage = playerRepository.findAll(pageable);

        // Assert the results
        assertThat(playerPage).isEmpty();
    }

    @Test
    public void findAll_whenPageNumberIsOutOfBounds_ReturnEmptyPage() {
        // Execute the method to test with an out-of-bounds page number
        Pageable pageable = PageRequest.of(10, 1); // Page 10 with 1 item per page
        Page<Player> playerPage = playerRepository.findAll(pageable);

        // Assert the results
        assertThat(playerPage).isEmpty();
        assertThat(playerPage.getTotalElements()).isEqualTo(2); // Adjust according to actual data count
        assertThat(playerPage.getTotalPages()).isEqualTo(2); // Adjust according to actual data count
    }


    @Test
    public void findById_whenPlayerExistsById_ReturnPlayer() {
        // Execute the method to test
        Optional<Player> player = playerRepository.findById("qwde");

        // Assert the results
        assertThat(player).isPresent();
        assertThat(player.get().getPlayerID()).isEqualTo("qwde");
    }

    @Test
    public void whenPlayerDoesNotExistById_thenFindByIdShouldReturnEmpty() {
        // Execute the method to test
        Optional<Player> player = playerRepository.findById("999");

        // Assert the results
        assertThat(player).isNotPresent();
    }

    @Test
    public void whenPlayerWithSpecialCharacterIdExists_thenFindByIdShouldReturnPlayer() {
        Player playerWithSpecialId = new Player();
        String id = "' OR 1=1 --";
        playerWithSpecialId.setPlayerID(id);
        playerRepository.save(playerWithSpecialId);

        // Execute the method to test
        Optional<Player> player = playerRepository.findById(id);

        // Assert the results
        assertThat(player).isPresent();
    }

}

