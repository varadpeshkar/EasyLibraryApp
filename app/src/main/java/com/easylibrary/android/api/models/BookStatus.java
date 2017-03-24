package com.easylibrary.android.api.models;

/**
 * Created by rohan on 24/3/17.
 */

public class BookStatus {

    private int color;

    private String msg;

    public BookStatus(int color, String msg) {
        this.color = color;
        this.msg = msg;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
