package com.example.matchmanagement.exception;

public class MatchListNotFoundException extends RuntimeException {

    public MatchListNotFoundException() {
        super("There are no matches currently in database");
    }

}
