package com.example.matchmanagement.controller;

import com.example.matchmanagement.exception.InvalidRequestException;
import com.example.matchmanagement.exception.MatchListNotFoundException;
import com.example.matchmanagement.exception.MatchNotFoundException;
import com.example.matchmanagement.model.MatchDto;
import com.example.matchmanagement.service.MatchManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/match")
@Slf4j
@RequiredArgsConstructor
public class MatchManagementController {

    @Autowired
    MatchManagementService matchManagementService;

    @PostMapping("/")
    @CacheEvict(value = "matches", allEntries = true)
    public ResponseEntity<?> createMatch(@RequestBody MatchDto request) {
        log.info("Creating match: " + request);
            matchManagementService.createMatch(request);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @Cacheable("matches")
    public ResponseEntity<List<MatchDto>> getAllMatches() {
        log.info("Fetching all matches");
        List<MatchDto> matchDtoList = matchManagementService.getAllMatches();
        if (!Objects.isNull(matchDtoList) && !matchDtoList.isEmpty()) {
            return new ResponseEntity<>(matchDtoList, HttpStatus.OK);
        } else {
            throw new MatchListNotFoundException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getMatchById(@PathVariable int id) {
        log.info("Fetching match by id: " + id);
        MatchDto result = matchManagementService.getMatchById(id);
        if (!Objects.isNull(result)) {
            return new ResponseEntity<>(matchManagementService.getMatchById(id), HttpStatus.OK);
        } else {
            throw new MatchNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "matches", allEntries = true)
    public ResponseEntity<MatchDto> deleteMatchById(@PathVariable int id) {
        log.info("Deleting match by id: " + id);
        MatchDto result = matchManagementService.deleteMatchById(id);
        if (!Objects.isNull(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);

        } else {
            throw new MatchNotFoundException(id);
        }

    }

    @DeleteMapping("/odds")
    @CacheEvict(value = "matches", allEntries = true)
    public ResponseEntity<?> deleteOdds(@RequestBody List<Integer> matchOddsIds) {
        log.info("Deleting odds: " + matchOddsIds);
        List<Integer> result = matchManagementService.deleteMatchOdds(matchOddsIds);
        if (!Objects.isNull(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);

        } else {
            throw new InvalidRequestException("Wrong odds ids.");
        }

    }

    @PatchMapping("/{id}")
    @CacheEvict(value = "matches", allEntries = true)
    public ResponseEntity<MatchDto> updateMatchByID(@PathVariable int id,
                                                    @RequestBody MatchDto request) {
        log.info("Updating match by id: " + id + " with new variables: " + request);
        MatchDto result = matchManagementService.updateMatchById(id, request);
        if (!Objects.isNull(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);

        } else {
            throw new MatchNotFoundException(id);
        }
    }
}
