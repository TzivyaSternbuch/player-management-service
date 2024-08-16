package com.intuit.Player.management.service.dto;

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
        this.sortProperty="playerID";
        this.sortDirection = SortDirection.ASC;
    }

    public enum SortDirection {
        ASC, DESC
    }

}

