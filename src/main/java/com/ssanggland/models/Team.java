package com.ssanggland.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Team implements Serializable {
    @Id
    @Column(name = "team_id")
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String record;

    @Column
    private int overall;

    @Column
    private String stadium;

    public Team(String name, String record, int overall, String stadium) {
        this.name = name;
        this.record = record;
        this.overall = overall;
        this.stadium = stadium;
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

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
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
}
