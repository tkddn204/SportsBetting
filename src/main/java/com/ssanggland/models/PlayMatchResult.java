package com.ssanggland.models;

import com.ssanggland.models.enumtypes.PlayMatchState;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PlayMatchResult implements Serializable {

//    @GenericGenerator(name = "generator", strategy = "foreign",
//            parameters = @Parameter(name = "property", value = "playMatch"))
    @Id
    @Column(name = "match_result_id")
    @GeneratedValue
    private long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private PlayMatch playMatch;

    @Column(name = "home_score")
    private int homeScore;

    @Column(name = "away_score")
    private int awayScore;

    public PlayMatchResult() {}

    public PlayMatchResult(PlayMatch playMatch, int homeScore, int awayScore) {
        this.playMatch = playMatch;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PlayMatch getPlayMatch() {
        return playMatch;
    }

    public void setPlayMatch(PlayMatch playMatch) {
        this.playMatch = playMatch;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    @Override
    public String toString() {
        if(playMatch.getState().equals(PlayMatchState.ENDGAME)) {
            return homeScore + " : " + awayScore;
        } else {
            return PlayMatchState.SOON.getDescription();
        }
    }
}
