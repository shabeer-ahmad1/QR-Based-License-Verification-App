package com.example.qrbasedlicense;

import android.graphics.Bitmap;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Artist implements Serializable {
    private String artistId;
    private String artistName;
    private String artistGenre;
    private long beginValidity;
    private long endValidity;
    private String imgpath;
    private String phoneNumber;

    public Artist(){

    }

    public Artist(String artistId, String artistName, String artistGenre,long beginValidity,long endValidity,String imgpath, String phoneNumber) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
        this.beginValidity=beginValidity;
        this.endValidity=endValidity;
        this.imgpath=imgpath;
        this.phoneNumber=phoneNumber;
    }

    public void setArtistGenre(String artistGenre) {
        this.artistGenre = artistGenre;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setBeginValidity(long beginValidity) {
        this.beginValidity = beginValidity;
    }

    public void setEndValidity(long endValidity) {
        this.endValidity = endValidity;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
    public long getBeginValidity() {
        return beginValidity;
    }
    public long getEndValidity() {
        return endValidity;
    }
    public String getImgpath(){ return imgpath;}


}

