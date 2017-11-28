package com.ssanggland.models.enumtypes;

public enum BettingState {
    PROCEED("경기진행중"),
    EXTEND("경기연장됨"),
    WAIT("지급대기중"),
    COMPLETE("지급완료");

    private final String description;

    BettingState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
