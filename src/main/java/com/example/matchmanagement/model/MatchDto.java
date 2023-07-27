package com.example.matchmanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
public class MatchDto {

    private int id;
    private String description;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date matchDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime matchTime;
    private String teamA;
    private String teamB;
    private Sport sport;

    private List<MatchOddsDto> odds;
}
