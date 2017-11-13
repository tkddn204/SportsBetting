package com.ssanggland.models;

import com.ssanggland.models.enumtypes.MatchStadium;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Match implements Serializable {
    @Id
    @Column(name = "match_id")
    @GeneratedValue
    private long id;

    @Column(name = "home_team")
    private long homeTeam;

    @Column(name = "away_team")
    private long awayTeam;

    @Enumerated(EnumType.STRING)
    private MatchStadium matchStadium;

    @Column(name = "kickoff_date")
    private Date kickoffDate;

    @Column(name = "end_game_date")
    private Date endGameDate;

    public Match(long homeTeam, long awayTeam, MatchStadium matchStadium, Date kickoffDate, Date endGameDate) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchStadium = matchStadium;
        this.kickoffDate = kickoffDate;
        this.endGameDate = endGameDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(long homeTeam) {
        this.homeTeam = homeTeam;
    }

    public long getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(long awayTeam) {
        this.awayTeam = awayTeam;
    }

    public MatchStadium getMatchStadium() {
        return matchStadium;
    }

    public void setMatchStadium(MatchStadium matchStadium) {
        this.matchStadium = matchStadium;
    }

    public Date getKickoffDate() {
        return kickoffDate;
    }

    public void setKickoffDate(Date kickoffDate) {
        this.kickoffDate = kickoffDate;
    }

    public Date getEndGameDate() {
        return endGameDate;
    }

    public void setEndGameDate(Date endGameDate) {
        this.endGameDate = endGameDate;
    }
}
