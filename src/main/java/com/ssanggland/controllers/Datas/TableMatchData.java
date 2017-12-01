package com.ssanggland.controllers.Datas;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class TableMatchData {

    private LongProperty matchId;
    private StringProperty match;
    private StringProperty home_dividend;
    private StringProperty draw_dividend;
    private StringProperty away_dividend;

    public TableMatchData(LongProperty matchId, StringProperty match, StringProperty home_dividend,
                          StringProperty draw_dividend, StringProperty away_dividend) {
        this.matchId = matchId;
        this.match = match;
        this.home_dividend = home_dividend;
        this.draw_dividend = draw_dividend;
        this.away_dividend = away_dividend;
    }

    public LongProperty matchIdProperty() {
        return matchId;
    }

    public StringProperty matchProperty() {
        return match;
    }

    public StringProperty home_dividendProperty() {
        return home_dividend;
    }

    public StringProperty draw_dividendProperty() {
        return draw_dividend;
    }

    public StringProperty away_dividendProperty() { return away_dividend;  }
}
