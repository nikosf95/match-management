package com.example.matchmanagement;

import com.example.matchmanagement.entities.Match;
import com.example.matchmanagement.model.MatchDto;
import com.example.matchmanagement.model.MatchOddsDto;
import com.example.matchmanagement.model.Sport;
import com.example.matchmanagement.repository.MatchRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatchManagementIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    ModelMapper modelMapper;

    @BeforeAll
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        MatchOddsDto matchOddsDto = MatchOddsDto.builder()
                .matchId(1)
                .odd(1.5)
                .specifier("X")
                .id(1)
                .build();
        List<MatchOddsDto> matchOddsDtos = new ArrayList<>();
        matchOddsDtos.add(matchOddsDto);

        MatchDto matchDto = MatchDto.builder()
                .sport(Sport.FOOTBALL)
                .teamB("pao")
                .teamA("OSFP")
                .id(1)
                .odds(matchOddsDtos)
                .build();

        MatchOddsDto secondMatchOddsDto = MatchOddsDto.builder()
                .matchId(2)
                .odd(1.5)
                .specifier("X")
                .id(2)
                .build();
        List<MatchOddsDto> secondMatchOddsDtos = new ArrayList<>();
        secondMatchOddsDtos.add(secondMatchOddsDto);

        MatchDto secondMatchDto = MatchDto.builder()
                .sport(Sport.FOOTBALL)
                .teamB("pao")
                .teamA("OSFP")
                .id(2)
                .odds(secondMatchOddsDtos)
                .build();

        Match match = modelMapper.map(matchDto, Match.class);
        Match secondMatch = modelMapper.map(secondMatchDto, Match.class);
        matchRepository.save(match);
        matchRepository.save(secondMatch);
    }

    @Test
    @Order(1)
    public void testCreateMatch() {
        String baseUrl = "http://localhost:" + port + "/api/v1/match/";

        MatchOddsDto matchOddsDto = MatchOddsDto.builder()
                .matchId(3)
                .odd(1.5)
                .specifier("X")
                .id(3)
                .build();
        List<MatchOddsDto> matchOddsDtos = new ArrayList<>();
        matchOddsDtos.add(matchOddsDto);

        MatchDto matchDto = MatchDto.builder()
                .sport(Sport.FOOTBALL)
                .teamB("pao")
                .teamA("OSFP")
                .id(3)
                .odds(matchOddsDtos)
                .build();

        ResponseEntity<MatchDto> response = restTemplate.postForEntity(baseUrl, matchDto, MatchDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    @Order(2)
    public void testGetMatchById() {
        int matchId = 1;
        String baseUrl = "http://localhost:" + port + "/api/v1/match/" + matchId;

        ResponseEntity<MatchDto> response = restTemplate.getForEntity(baseUrl, MatchDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        MatchDto matchDto = response.getBody();
        assertNotNull(matchDto);
    }

    @Test
    @Order(3)
    public void testUpdateMatch() {

        int matchId = 1;
        String baseUrl = "http://localhost:" + port + "/api/v1/match/" + matchId;
        MatchDto updatedMatchDto = MatchDto.builder()
                .teamA("paok")
                .teamB("aek")
                .build();

        MatchDto response = restTemplate.patchForObject(baseUrl, updatedMatchDto, MatchDto.class);
        assertNotNull(response);
    }

    @Test
    @Order(4)
    public void testDeleteMatch() {

        int matchId = 2;
        String baseUrl = "http://localhost:" + port + "/api/v1/match/" + matchId;
        ResponseEntity<Void> resp = restTemplate.exchange(baseUrl, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }
}
