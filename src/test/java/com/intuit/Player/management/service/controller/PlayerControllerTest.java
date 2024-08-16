package com.intuit.Player.management.service.controller;

import com.intuit.Player.management.service.dto.PlayerRequestParams;
import com.intuit.Player.management.service.entity.Player;
import com.intuit.Player.management.service.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    private List<Player> playerList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        Player player1 = new Player();
        player1.setPlayerID("1");
        player1.setNameFirst("Player One");
        Player player2 = new Player();
        player1.setPlayerID("2");
        player1.setNameFirst("Player Two");
        playerList = Arrays.asList(player1, player2);

        // Sample request params
        PlayerRequestParams playerRequestParams = new PlayerRequestParams();
        playerRequestParams.setPage(0);
        playerRequestParams.setSize(2);
        playerRequestParams.setSortProperty("name");
        playerRequestParams.setSortDirection(PlayerRequestParams.SortDirection.ASC);
    }

    @Test
    void testGetAllPlayers() throws Exception {
        Page<Player> page = new PageImpl<>(playerList);
        when(playerService.getAllPlayers(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/players")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sortProperty", "playerID")
                        .param("sortDirection", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void testGetAllPlayers_InvalidPaginationParameters() throws Exception {
        mockMvc.perform(get("/api/players")
                        .param("page", "-1")
                        .param("size", "1000"))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertInstanceOf(IllegalArgumentException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Page index must not be less than zero", result.getResolvedException().getMessage()));;
    }

    @Test
    void testGetAllPlayers_EmptyDatabase() throws Exception {
        when(playerService.getAllPlayers(any(Pageable.class))).thenReturn(Page.empty());

        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    void testGetPlayerById() throws Exception {
        Player player = new Player();
        player.setPlayerID("1a");
        player.setNameFirst("Player One");

        when(playerService.getPlayerById("1a")).thenReturn(player);

        mockMvc.perform(get("/api/players/1a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerID", is("1a")))
                .andExpect(jsonPath("$.nameFirst", is("Player One")));
    }

}
