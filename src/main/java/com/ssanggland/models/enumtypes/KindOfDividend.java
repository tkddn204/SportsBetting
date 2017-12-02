package com.ssanggland.models.enumtypes;

public enum KindOfDividend {
    HOME("홈팀"),
    DRAW("무승부"),
    AWAY("어웨이팀");

    private final String description;

    KindOfDividend(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
