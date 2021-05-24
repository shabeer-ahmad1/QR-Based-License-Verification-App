package com.example.qrbasedlicense;

import java.io.Serializable;

public class Chalan implements Serializable {
    private String chalanID;
    private String chalanTitle;
    private String chalanAmount;
    private long chalanDate;


    public Chalan(){

    }

    public Chalan(String chalanID, String chalanTitle, String chalanAmount,long chalanDate) {
        this.chalanID = chalanID;
        this.chalanTitle = chalanTitle;
        this.chalanAmount = chalanAmount;
        this.chalanDate=chalanDate;

    }
    public String getChalanID() {
        return chalanID;
    }


    public String getChalanTitle() {
        return chalanTitle;
    }

    public String getChalanAmount() {
        return chalanAmount;
    }

    public long getChalanDate() {
        return chalanDate;
    }



}
