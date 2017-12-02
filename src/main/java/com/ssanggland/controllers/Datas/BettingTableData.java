package com.ssanggland.controllers.Datas;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class BettingTableData {
    private LongProperty bettingId;
    private StringProperty bettingMatch;
    private StringProperty resultMatchState;
    private StringProperty matchDate;
    private StringProperty bettingState;
    private LongProperty bettingMoney;
    private LongProperty bettingResultMoney;


    public BettingTableData(LongProperty bettingId, StringProperty bettingMatch,
                            StringProperty resultMatchState, StringProperty matchDate,
                            StringProperty bettingState, LongProperty bettingMoney,
                            LongProperty bettingResultMoney) {
        this.bettingId = bettingId;
        this.bettingMatch = bettingMatch;
        this.resultMatchState = resultMatchState;
        this.matchDate = matchDate;
        this.bettingState = bettingState;
        this.bettingMoney = bettingMoney;
        this.bettingResultMoney = bettingResultMoney;
    }

    public LongProperty bettingIdProperty() {
        return bettingId;
    }

    public StringProperty bettingMatchProperty() {
        return bettingMatch;
    }

    public StringProperty resultMatchStateProperty() {
        return resultMatchState;
    }

    public StringProperty matchDateProperty() {
        return matchDate;
    }

    public StringProperty bettingStateProperty() {
        return bettingState;
    }

    public LongProperty bettingMoneyProperty() {
        return bettingMoney;
    }

    public LongProperty bettingResultMoneyProperty() {
        return bettingResultMoney;
    }
}