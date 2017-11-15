package com.ssanggland.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Betting implements Serializable {

    private long id;
    private User user;
    private long bettingMoney;
    private Date bettingTime;
    private String bettingResult;

    public Betting(User user, long bettingMoney, Date bettingTime, String bettingResult) {
        this.user = user;
        this.bettingMoney = bettingMoney;
        this.bettingTime = bettingTime;
        this.bettingResult = bettingResult;
    }

    @Id
    @Column(name = "betting_id")
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "betting_money")
    public long getBettingMoney() {
        return bettingMoney;
    }

    public void setBettingMoney(long bettingMoney) {
        this.bettingMoney = bettingMoney;
    }

    @Temporal(TemporalType.DATE)
    public Date getBettingTime() {
        return bettingTime;
    }

    public void setBettingTime(Date bettingTime) {
        this.bettingTime = bettingTime;
    }

    @Column(name = "betting_result")
    public String getBettingResult() {
        return bettingResult;
    }

    public void setBettingResult(String bettingResult) {
        this.bettingResult = bettingResult;
    }
}
