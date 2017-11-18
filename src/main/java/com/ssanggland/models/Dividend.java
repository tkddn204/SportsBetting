package com.ssanggland.models;

import com.ssanggland.models.enumtypes.KindOfDividend;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Dividend implements Serializable {
    @Id
    @Column(name = "dividend_id")
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private KindOfDividend kindOfDividend;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private PlayMatch playMatch;

    @Column
    private float dividendRate;

    @OneToMany(mappedBy = "dividend")
    private Set<Betting> bettings = new HashSet<>();

    public Dividend(KindOfDividend kindOfDividend, float dividendRate) {
        this.kindOfDividend = kindOfDividend;
        this.dividendRate = dividendRate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public KindOfDividend getKindOfDividend() {
        return kindOfDividend;
    }

    public void setKindOfDividend(KindOfDividend kindOfDividend) {
        this.kindOfDividend = kindOfDividend;
    }

    public float getDividendRate() {
        return dividendRate;
    }

    public void setDividendRate(float dividendRate) {
        this.dividendRate = dividendRate;
    }

    public PlayMatch getPlayMatch() {
        return playMatch;
    }

    public void setPlayMatch(PlayMatch playMatch) {
        this.playMatch = playMatch;
    }

    public Set<Betting> getBettings() {
        return bettings;
    }

    public void setBettings(Set<Betting> bettings) {
        this.bettings = bettings;
    }
}
