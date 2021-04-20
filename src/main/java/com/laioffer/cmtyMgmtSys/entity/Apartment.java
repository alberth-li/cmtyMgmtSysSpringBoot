package com.laioffer.cmtyMgmtSys.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "apartment")
public class Apartment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String aptNum;
    private boolean avail;

    public int getId() {
        return id;
    }

    public String getAptNum() {
        return aptNum;
    }

    public boolean isAvail() {
        return avail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAptNum(String aptNum) {
        this.aptNum = aptNum;
    }

    public void setAvail(boolean avail) {
        this.avail = avail;
    }
}
