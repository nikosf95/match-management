package com.example.matchmanagement.repository;

import com.example.matchmanagement.entities.MatchOdds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchOddsRepository extends JpaRepository<MatchOdds, Integer> {
}
