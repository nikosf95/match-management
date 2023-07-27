package com.example.matchmanagement.model;

import lombok.Data;

@Data
public class MatchOddsDto {

    private int id;
    private int matchId;
    private String specifier;
    private double odd;
}
