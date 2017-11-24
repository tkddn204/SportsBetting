package com.ssanggland.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class League {
    @Id
    @Column(name = "player_id")
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "league")
    private Set<Team> teamList;
    
    public League(String name, Set<Team> teamList) {
        this.name = name;
        this.teamList = teamList;
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

    public Set<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(Set<Team> teamList) {
        this.teamList = teamList;
    }
}
