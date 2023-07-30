package com.example.matchmanagement.service;

import com.example.matchmanagement.entities.Match;
import com.example.matchmanagement.entities.MatchOdds;
import com.example.matchmanagement.model.MatchDto;
import com.example.matchmanagement.model.MatchOddsDto;
import com.example.matchmanagement.repository.MatchOddsRepository;
import com.example.matchmanagement.repository.MatchRepository;
import com.example.matchmanagement.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
    MatchOddsRepository matchOddsRepository;
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
           Match updatedMatch = utils.updateMatchFromMatchDto(existingMatch.get(), match);
            List<MatchOddsDto> oddsList = match.getOdds();
            if (oddsList != null) {
                for (MatchOddsDto oddsDto : oddsList) {
                    MatchOdds existingOdds = updatedMatch.getOdds().stream()
                            .filter(odds -> odds.getId() == oddsDto.getId())
                            .findFirst()
                            .orElse(null);
                    if (existingOdds != null) {
                        BeanUtils.copyProperties(oddsDto, existingOdds);
                    }
                }
            }

            matchRepository.save(updatedMatch);
            MatchDto matchDto = modelMapper.map(updatedMatch, MatchDto.class);
            return matchDto;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Integer> deleteMatchOdds(List<Integer> matchOddsIds) {

        try {
            matchOddsRepository.deleteByIdIn(matchOddsIds);
            return matchOddsIds;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
