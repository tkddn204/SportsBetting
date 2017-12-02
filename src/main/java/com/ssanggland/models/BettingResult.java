package com.ssanggland.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class BettingResult implements Serializable {
//    @GenericGenerator(name = "generator", strategy = "foreign",
//            parameters = @Parameter(name = "property", value = "betting"))
    @Id
    @Column(name = "betting_result_id")
    @GeneratedValue
    private long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Betting betting;

    @Column(name = "result_money")
    private long resultMoney;

    @Column(name = "is_paid")
    private boolean isPaid = false;

    public BettingResult() {}

    public BettingResult(Betting betting, long resultMoney) {
        this.betting = betting;
        this.resultMoney = resultMoney;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Betting getBetting() {
        return betting;
    }

    public void setBetting(Betting betting) {
        this.betting = betting;
    }

    public long getResultMoney() {
        return resultMoney;
    }

    public void setResultMoney(long resultMoney) {
        this.resultMoney = resultMoney;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
