package com.example.matchmanagement.service;

import com.example.matchmanagement.entities.Match;
import com.example.matchmanagement.entities.MatchOdds;
import com.example.matchmanagement.model.MatchDto;
import com.example.matchmanagement.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatchManagementServiceImpl implements MatchManagementService{

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public MatchDto createMatch(MatchDto request) {
        var matchEntity = modelMapper.map(request, Match.class);
        List<MatchOdds> matchOddsList = request.getOdds()
                .stream()
                .map(matchOddsDto -> modelMapper.map(matchOddsDto, MatchOdds.class))
                .collect(Collectors.toList());
        matchOddsList.forEach(matchOdds -> matchOdds.setMatch(matchEntity));
        matchEntity.setOdds(matchOddsList);

        try{
            matchRepository.save(matchEntity);
        } catch (Exception ex) {
            log.info("Error occured: " + ex);
        }
       return request;
    }

    @Override
    public List<MatchDto> getAllMatches() {
        var matchEntities = matchRepository.findAll();
        List<MatchDto> matchDtos = matchEntities.stream()
                .map(match -> modelMapper.map(match, MatchDto.class))
                .collect(Collectors.toList());

        return matchDtos;
    }

    @Override
    public MatchDto getMatchById(int id) {
        var matchEntity = matchRepository.findById(id);
        if (!matchEntity.isEmpty()) {
            var matchDto = modelMapper.map(matchEntity.get(), MatchDto.class);
            return matchDto;
        } else {
            return null;
        }

    }

    @Override
    public MatchDto deleteMatchById(int id) {
        var matchEntity = matchRepository.findById(id);
        MatchDto matchDto = null;
        if (!matchEntity.isEmpty()) {
            matchDto = modelMapper.map(matchEntity.get(), MatchDto.class);
            matchRepository.deleteById(id);
        }
        return matchDto;
    }
}
