package com.ssanggland.models.enumtypes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum MatchStadium {
    HOME("홈"), AWAY("어웨이");

    private final String description;

    MatchStadium(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    private static final List<MatchStadium> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static MatchStadium randomMatchStadium() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
