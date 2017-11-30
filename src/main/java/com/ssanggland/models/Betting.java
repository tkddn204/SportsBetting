package com.ssanggland.models;

import com.ssanggland.models.enumtypes.BettingState;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Betting implements Serializable {

    @Id
    @Column(name = "betting_id")
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "dividend_id")
    private Dividend dividend;

    @Column(name = "betting_money")
    private long bettingMoney;

    @Temporal(TemporalType.DATE)
    private Date bettingTime = new Date();

    @Enumerated(EnumType.STRING)
    private BettingState state = BettingState.YET;

    public Betting() {}

    public Betting(User user, Dividend dividend, long bettingMoney) {
        this.user = user;
        this.dividend = dividend;
        this.bettingMoney = bettingMoney;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dividend getDividend() {
        return dividend;
    }

    public void setDividend(Dividend dividend) {
        this.dividend = dividend;
    }

    public long getBettingMoney() {
        return bettingMoney;
    }

    public void setBettingMoney(long bettingMoney) {
        this.bettingMoney = bettingMoney;
    }

    public Date getBettingTime() {
        return bettingTime;
    }

    public void setBettingTime(Date bettingTime) {
        this.bettingTime = bettingTime;
    }

    public BettingState getState() {
        return state;
    }

    public void setState(BettingState state) {
        this.state = state;
    }
}
