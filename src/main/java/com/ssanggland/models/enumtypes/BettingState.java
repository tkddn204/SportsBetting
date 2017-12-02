package com.ssanggland.models.enumtypes;

public enum BettingState {
    YET("대기중"),
    FAIL("실패"),
    GOOD("적중");

    private final String description;

    BettingState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
