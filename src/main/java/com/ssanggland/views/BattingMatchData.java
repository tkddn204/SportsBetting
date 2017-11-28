package com.ssanggland.views;

import javafx.beans.property.StringProperty;

public class BattingMatchData {
    private StringProperty battingMatch;
    private StringProperty matchState;
    private StringProperty battingResult;
    private StringProperty battingMoney;

    public BattingMatchData(StringProperty battingMatch, StringProperty matchState, StringProperty battingResult, StringProperty battingMoney) {
        this.battingMatch = battingMatch;
        this.matchState = matchState;
        this.battingResult = battingResult;
        this.battingMoney = battingMoney;
    }

    public String getBattingMatch() {
        return battingMatch.get();
    }

    public StringProperty battingMatchProperty() {
        return battingMatch;
    }

    public void setBattingMatch(String battingMatch) {
        this.battingMatch.set(battingMatch);
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

    public String getBattingResult() {
        return battingResult.get();
    }

    public StringProperty battingResultProperty() {
        return battingResult;
    }

    public void setBattingResult(String battingResult) {
        this.battingResult.set(battingResult);
    }

    public String getBattingMoney() {
        return battingMoney.get();
    }

    public StringProperty battingMoneyProperty() {
        return battingMoney;
    }

    public void setBattingMoney(String battingMoney) {
        this.battingMoney.set(battingMoney);
    }
}