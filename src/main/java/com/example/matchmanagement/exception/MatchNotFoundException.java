package com.example.matchmanagement.exception;

import lombok.Data;

public class MatchNotFoundException extends RuntimeException {
    private int matchId;

    public MatchNotFoundException(int matchId) {
        super("Match with id: " + matchId + " not found" );
        this.matchId = matchId;
    }

    public int getMatchId() {
        return matchId;
    }
}
