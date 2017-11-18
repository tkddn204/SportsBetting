package com.ssanggland.models;

import com.ssanggland.models.enumtypes.MatchStadium;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PlayMatch implements Serializable {
    @Id
    @Column(name = "match_id")
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @Enumerated(EnumType.STRING)
    private MatchStadium matchStadium;

    @Column(name = "kickoff_date")
    private Date kickoffDate;

    @Column(name = "end_game_date")
    private Date endGameDate;

    public PlayMatch(Team homeTeam, Team awayTeam, MatchStadium matchStadium, Date kickoffDate, Date endGameDate) {
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

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
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
