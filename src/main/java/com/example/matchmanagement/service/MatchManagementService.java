package com.example.matchmanagement.service;

import com.example.matchmanagement.model.MatchDto;

import java.util.List;

public interface MatchManagementService {

    void createMatch(MatchDto request);
    List<MatchDto> getAllMatches();

    MatchDto getMatchById(int id);

    MatchDto deleteMatchById(int id);

    MatchDto updateMatchById(int id, MatchDto match);

    List<Integer> deleteMatchOdds(List<Integer> matchOddsIds);
}
