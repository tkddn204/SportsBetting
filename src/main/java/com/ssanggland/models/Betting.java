package com.ssanggland.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Betting implements Serializable {
    @Id
    @Column(name = "betting_id")
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "betting_money")
    private long bettingMoney;

    @Temporal(TemporalType.DATE)
    private Date bettingTime;

    @Column(name = "betting_result")
    private String bettingResult;

    public Betting(User user, long bettingMoney, Date bettingTime, String bettingResult) {
        this.user = user;
        this.bettingMoney = bettingMoney;
        this.bettingTime = bettingTime;
        this.bettingResult = bettingResult;
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

    public String getBettingResult() {
        return bettingResult;
    }

    public void setBettingResult(String bettingResult) {
        this.bettingResult = bettingResult;
    }
}
