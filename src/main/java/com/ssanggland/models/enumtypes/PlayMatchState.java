package com.ssanggland.models.enumtypes;

public enum PlayMatchState {
    SOON("경기 시작전"),
    PROCEED("경기 진행중"),
    EXTEND("경기 연장됨"),
    ENDGAME("경기 완료");

    private final String description;

    PlayMatchState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
