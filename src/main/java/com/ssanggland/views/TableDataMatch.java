package com.ssanggland.views;

import javafx.beans.property.StringProperty;

public class TableDataMatch {

    private StringProperty match;
    private StringProperty home_dividend;
    private StringProperty draw_dividend;
    private StringProperty away_dividend;

    public TableDataMatch(StringProperty match, StringProperty home_dividend, StringProperty draw_dividend, StringProperty away_dividend) {
        this.match = match;
        this.home_dividend = home_dividend;
        this.draw_dividend = draw_dividend;
        this.away_dividend = away_dividend;
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

    public StringProperty away_dividendProperty() {
        return away_dividend;
    }
}