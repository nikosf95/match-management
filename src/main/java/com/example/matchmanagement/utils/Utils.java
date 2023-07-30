package com.example.matchmanagement.utils;

import com.example.matchmanagement.entities.Match;
import com.example.matchmanagement.entities.MatchOdds;
import com.example.matchmanagement.model.MatchDto;
import com.example.matchmanagement.model.MatchOddsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class Utils {

    public Match updateMatchFromMatchDto(Match match, MatchDto matchDto) {

        if (matchDto.getDescription() != null) {
            match.setDescription(matchDto.getDescription());
        }

        if (matchDto.getMatchDate() != null) {
            match.setMatchDate(matchDto.getMatchDate());
        }

        if (matchDto.getMatchTime() != null) {
            match.setMatchTime(matchDto.getMatchTime());
        }

        if (matchDto.getTeamA() != null) {
            match.setTeamA(matchDto.getTeamA());
        }

        if (matchDto.getTeamB() != null) {
            match.setTeamB(matchDto.getTeamB());
        }

        if (matchDto.getSport() != null) {
            match.setSport(matchDto.getSport());
        }

        return match;
    }
}
