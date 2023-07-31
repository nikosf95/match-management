package com.example.matchmanagement.utils;

import com.example.matchmanagement.entities.Match;
import com.example.matchmanagement.entities.MatchOdds;
import com.example.matchmanagement.exception.InvalidRequestException;
import com.example.matchmanagement.model.MatchDto;
import com.example.matchmanagement.model.MatchOddsDto;
import com.example.matchmanagement.model.Sport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class Utils {

    public Match updateMatchFromMatchDto(Match match, MatchDto matchDto) {

        if (StringUtils.hasText(matchDto.getDescription())) {
            match.setDescription(matchDto.getDescription());
        }

        if (!Objects.isNull(matchDto.getMatchDate())) {
            match.setMatchDate(matchDto.getMatchDate());
        }

        if (!Objects.isNull(matchDto.getMatchTime())) {
            match.setMatchTime(matchDto.getMatchTime());
        }

        if (StringUtils.hasText(matchDto.getTeamA())) {
            match.setTeamA(matchDto.getTeamA());
        }

        if (StringUtils.hasText(matchDto.getTeamB())) {
            match.setTeamB(matchDto.getTeamB());
        }

        if (StringUtils.hasText(matchDto.getSport())) {
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

    public void validateRequest(MatchDto matchDto) {
        if (StringUtils.hasText(matchDto.getSport()) && !(matchDto.getSport().equalsIgnoreCase("1") || matchDto.getSport().equalsIgnoreCase("2"))) {
            throw new InvalidRequestException("Sport should have values 1 (for Football) or 2 (for Basketball)");
        }
        if (!Objects.isNull(matchDto.getOdds()) && !matchDto.getOdds().isEmpty()) {
            List<MatchOddsDto> matchOddsDtoList = matchDto.getOdds().stream()
                    .filter(o -> o.getSpecifier().equalsIgnoreCase("1") || o.getSpecifier().equalsIgnoreCase("2") || o.getSpecifier().equalsIgnoreCase("X"))
                    .collect(Collectors.toList());
            if (matchOddsDtoList.isEmpty()) {
                throw new InvalidRequestException("Specifier field must be 1, 2 or X");
            }
        }

    }
}
