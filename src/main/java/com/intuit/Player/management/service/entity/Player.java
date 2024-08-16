package com.intuit.Player.management.service.entity;


import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Player {
    @Id
    private String playerID;

    private String birthYear;

    private String birthMonth;

    private String birthDay;

    private String birthCountry;

    private String birthState;

    private String birthCity;

    private String deathYear;

    private String deathMonth;

    private String deathDay;

    private String deathCountry;

    private String deathState;

    private String deathCity;

    private String nameFirst;

    private String nameLast;

    private String nameGiven;

    private String weight;

    private String height;

    private String bats;

    private String throwsType;

    private String debut;

    private String finalGame;

    private String retroID;

    private String bbrefID;

}
