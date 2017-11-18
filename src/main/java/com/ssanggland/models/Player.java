package com.ssanggland.models;

import com.ssanggland.models.enumtypes.PositionOfPlayer;

import javax.persistence.*;

@Entity
public class Player {
    @Id
    @Column(name = "player_id")
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private int age;

    @Enumerated(EnumType.STRING)
    private PositionOfPlayer position;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Team team;

    public Player(String name, int age, PositionOfPlayer position, Team team) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.team = team;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PositionOfPlayer getPosition() {
        return position;
    }

    public void setPosition(PositionOfPlayer position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
