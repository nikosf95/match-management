package com.example.matchmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@Table(name = "match_odds")
@NoArgsConstructor
@AllArgsConstructor
public class MatchOdds {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="match_id", nullable=false)
    private Match match;
    @Column(name = "specifier")
    private String specifier;
    @Column(name = "odd")
    private double odd;


}
