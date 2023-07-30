package com.example.matchmanagement.utils;

import com.example.matchmanagement.entities.Match;
import com.example.matchmanagement.entities.MatchOdds;
import com.example.matchmanagement.model.MatchDto;
import com.example.matchmanagement.model.MatchOddsDto;
import com.example.matchmanagement.model.Sport;
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
            match.setSport(Sport.valueOf(matchDto.getSport()));
        }

        return match;
    }


    public static MatchDto mapMatchEntitiesToListMatch(Match match) {

        List<MatchOddsDto> oddsDtoList = match.getOdds()
                .stream()
                .map(Utils::matchOddsToDtos)
                .collect(Collectors.toList());

        return MatchDto.builder()
                .id(match.getId())
                .description(match.getDescription())
                .matchDate(match.getMatchDate())
                .matchTime(match.getMatchTime())
                .teamA(match.getTeamA())
                .teamB(match.getTeamB())
                .sport(match.getSport().toString())
                .odds(oddsDtoList)
                .build();
    }

    public static MatchOddsDto matchOddsToDtos(MatchOdds odds) {
        return MatchOddsDto.builder()
                .id(odds.getId())
                .matchId(odds.getMatch().getId())
                .specifier(odds.getSpecifier())
                .odd(odds.getOdd())
                .build();
    }

    public static Match matchDtoToEntity(MatchDto matchDto) {

        return Match.builder()
                .id(matchDto.getId())
                .description(matchDto.getDescription())
                .matchDate(matchDto.getMatchDate())
                .matchTime(matchDto.getMatchTime())
                .teamA(matchDto.getTeamA())
                .teamB(matchDto.getTeamB())
                .sport(Sport.fromValue(Integer.parseInt(matchDto.getSport())))
                .build();
    }

}
