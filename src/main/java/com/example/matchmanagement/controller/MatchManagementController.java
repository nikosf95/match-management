package com.example.matchmanagement.controller;

import com.example.matchmanagement.exception.MatchNotFoundException;
import com.example.matchmanagement.model.MatchDto;
import com.example.matchmanagement.service.MatchManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<MatchDto> createMatch(@RequestBody MatchDto request) {
        log.info("Creating match: " + request);
        return new ResponseEntity<>(matchManagementService.createMatch(request), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MatchDto>> getAllMatches() {
        log.info("Fetching all amtches");
        return new ResponseEntity<>(matchManagementService.getAllMatches(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getMatchById(@PathVariable int id) {
        log.info("Fetching match by id: " + id);
        var result = matchManagementService.getMatchById(id);
        if (!Objects.isNull(result)) {
            return new ResponseEntity<>(matchManagementService.getMatchById(id), HttpStatus.OK);
        } else {
            throw new MatchNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MatchDto> deleteMatchById(@PathVariable int id) {
        log.info("Deleting match by id: " + id);
        var result = matchManagementService.getMatchById(id);
        if (!Objects.isNull(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);

        } else {
            throw new MatchNotFoundException(id);
        }

    }
}
