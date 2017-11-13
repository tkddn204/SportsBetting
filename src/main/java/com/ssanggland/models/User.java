package com.ssanggland.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private int password;

    @Column
    private long money;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate = new Date();

    public User(String name, int password, long money, Date createDate) {
        this.name = name;
        this.password = password;
        this.money = money;
        this.createDate = createDate;
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

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
