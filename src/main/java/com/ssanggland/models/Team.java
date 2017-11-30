package com.ssanggland.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Team implements Serializable {
    @Id
    @Column(name = "team_id")
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private int overall;

    @Column
    private String stadium;

    @Column
    private int win;

    @Column
    private int draw;

    @Column
    private int lose;

    @Column
    private int score;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private League league;

    public Team() {}

    public Team(String name, int overall, String stadium, int win, int draw, int lose, int score, League league) {
        this.name = name;
        this.overall = overall;
        this.stadium = stadium;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
        this.score = score;
        this.league = league;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOverall() {
        return overall;
    }

    public void setOverall(int overall) {
        this.overall = overall;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
