package com.ssanggland.models.enumtypes;

public enum BettingState {
    YET("대기중"),
    LOW("실패-낮음"),
    GOOD("적중"),
    HIGH("실패-높음");

    private final String description;

    BettingState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
