package com.ssanggland.views;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class BettingMatchData {
    private LongProperty bettingId;
    private StringProperty bettingMatch;
    private StringProperty matchState;
    private StringProperty bettingState;
    private LongProperty bettingMoney;
    private LongProperty bettingResultMoney;

    public BettingMatchData(LongProperty bettingId, StringProperty bettingMatch,
                            StringProperty matchState, StringProperty bettingState,
                            LongProperty bettingMoney, LongProperty bettingResultMoney) {
        this.bettingId = bettingId;
        this.bettingMatch = bettingMatch;
        this.matchState = matchState;
        this.bettingState = bettingState;
        this.bettingMoney = bettingMoney;
        this.bettingResultMoney = bettingResultMoney;
    }

    public long getBettingId() {
        return bettingId.get();
    }

    public LongProperty bettingIdProperty() {
        return bettingId;
    }

    public void setBettingId(long bettingId) {
        this.bettingId.set(bettingId);
    }

    public String getBettingMatch() {
        return bettingMatch.get();
    }

    public StringProperty bettingMatchProperty() {
        return bettingMatch;
    }

    public void setBettingMatch(String bettingMatch) {
        this.bettingMatch.set(bettingMatch);
    }

    public String getMatchState() {
        return matchState.get();
    }

    public StringProperty matchStateProperty() {
        return matchState;
    }

    public void setMatchState(String matchState) {
        this.matchState.set(matchState);
    }

    public String getBettingState() {
        return bettingState.get();
    }

    public StringProperty bettingStateProperty() {
        return bettingState;
    }

    public void setBettingState(String bettingState) {
        this.bettingState.set(bettingState);
    }

    public long getBettingMoney() {
        return bettingMoney.get();
    }

    public LongProperty bettingMoneyProperty() {
        return bettingMoney;
    }

    public void setBettingMoney(long bettingMoney) {
        this.bettingMoney.set(bettingMoney);
    }

    public long getBettingResultMoney() {
        return bettingResultMoney.get();
    }

    public LongProperty bettingResultMoneyProperty() {
        return bettingResultMoney;
    }

    public void setBettingResultMoney(long bettingResultMoney) {
        this.bettingResultMoney.set(bettingResultMoney);
    }
}