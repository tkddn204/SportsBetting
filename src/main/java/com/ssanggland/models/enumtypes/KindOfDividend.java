package com.ssanggland.models.enumtypes;

public enum KindOfDividend {
    WIN("승리"),
    DRAW("무승부"),
    LOSE("패배");

    private final String description;

    KindOfDividend(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
