package com.example.matchmanagement.utils;

import com.example.matchmanagement.entities.Match;
import com.example.matchmanagement.model.MatchDto;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public Match mapCreateMatchRequestToMatchOdds(MatchDto request) {

        return Match.builder()
                .description(request.getDescription())
                .matchDate(request.getMatchDate())
                .matchTime(request.getMatchTime())
                .teamA(request.getTeamA())
                .teamB(request.getTeamB())
                .sport(request.getSport())
                //.odds(request.getMatchOddsList())
                .build();

    }

//    public List<MatchOdds> mapMatchOddsDtoToMatchOdds(CreateMatchRequest request) {
//        request.getMatchOddsList().stream().map(s -> )
//        List<MatchOdds> matchOddsList = MatchOdds.builder()
//                .matchId()
//                .
//    }
}
