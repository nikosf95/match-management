package com.example.matchmanagement.entities;

import com.example.matchmanagement.model.Sport;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name = "description")
    private String description;
    @Column(name = "match_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date matchDate;
    @Column(name = "match_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime matchTime;
    @Column(name = "team_a")
    private String teamA;
    @Column(name = "team_b")
    private String teamB;
    @Column(name = "sport")
    @Enumerated(EnumType.ORDINAL)
    private Sport sport;

    @OneToMany(mappedBy="match", cascade = CascadeType.ALL)
    private List<MatchOdds> odds;

}
