package com.example.matchmanagement.model;

public enum Sport {
    None(0),
    Football(1),
    Basketball(2);

    private final int value;

    Sport(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Sport fromValue(int value) {
        for (Sport sport : Sport.values()) {
            if (sport.value == value) {
                return sport;
            }
        }
        throw new IllegalArgumentException("Invalid Sport value: " + value);
    }

}
