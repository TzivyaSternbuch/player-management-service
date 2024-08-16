package com.intuit.Player.management.service.dto;

import com.intuit.Player.management.service.service.PlayerService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerRequestParams {

    private int page;
    private int size;
    private String sortProperty;
    private SortDirection sortDirection;

    // Default constructor
    public PlayerRequestParams() {
        this.page = 0;
        this.size = 10;
        this.sortProperty= PlayerService.PLAYER_ID;
        this.sortDirection = SortDirection.ASC;
    }

    public enum SortDirection {
        ASC, DESC
    }

}

