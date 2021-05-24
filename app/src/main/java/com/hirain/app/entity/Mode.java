package com.hirain.app.entity;

public class Mode {
    private int imgId;

    private String dec;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public Mode(String dec) {
        this.dec = dec;
    }
}
