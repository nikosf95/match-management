package com.example.matchmanagement.service;

import com.example.matchmanagement.entities.Match;
import com.example.matchmanagement.entities.MatchOdds;
import com.example.matchmanagement.model.MatchDto;
import com.example.matchmanagement.repository.MatchRepository;
import com.example.matchmanagement.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatchManagementServiceImpl implements MatchManagementService {

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    Utils utils;

    @Override
    public void createMatch(MatchDto request) {
        Match matchEntity = modelMapper.map(request, Match.class);
        List<MatchOdds> matchOddsList = request.getOdds()
                .stream()
                .map(matchOddsDto -> modelMapper.map(matchOddsDto, MatchOdds.class))
                .collect(Collectors.toList());
        matchOddsList.forEach(matchOdds -> matchOdds.setMatch(matchEntity));
        matchEntity.setOdds(matchOddsList);
        matchRepository.save(matchEntity);
    }

    @Override
    public List<MatchDto> getAllMatches() {
        List<Match> matchEntities = matchRepository.findAll();
        List<MatchDto> matchDtos = matchEntities.stream()
                .map(match -> modelMapper.map(match, MatchDto.class))
                .collect(Collectors.toList());

        return matchDtos;
    }

    @Override
    public MatchDto getMatchById(int id) {
        Optional<Match> matchEntity = matchRepository.findById(id);
        if (!matchEntity.isEmpty()) {
            MatchDto matchDto = modelMapper.map(matchEntity.get(), MatchDto.class);
            return matchDto;
        } else {
            return null;
        }

    }

    @Override
    public MatchDto deleteMatchById(int id) {
        Optional<Match> matchEntity = matchRepository.findById(id);
        MatchDto matchDto = null;
        if (!matchEntity.isEmpty()) {
            matchDto = modelMapper.map(matchEntity.get(), MatchDto.class);
            matchRepository.deleteById(id);
        }
        return matchDto;
    }

    @Override
    public MatchDto updateMatchById(int id, MatchDto match) {
        Optional<Match> existingMatch = matchRepository.findById(id);
        if (!existingMatch.isEmpty()) {
            Match matchEntity = utils.updateMatchFromMatchDto(existingMatch.get(), match);
            matchRepository.save(matchEntity);
            MatchDto matchDto = modelMapper.map(existingMatch.get(), MatchDto.class);
            return matchDto;
        } else {
            return null;
        }
    }
}
