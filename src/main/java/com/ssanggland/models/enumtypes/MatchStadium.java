package com.ssanggland.models.enumtypes;

public enum MatchStadium {
    HOME("홈"), AWAY("어웨이");

    private final String description;

    MatchStadium(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
