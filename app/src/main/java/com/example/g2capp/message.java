package com.example.g2capp;

public class message {
    public String key;

    public message(){

    }
    public message(String key){
        this.key=key;

    }

    public String getData() {
        return key;
    }
    public void setData(String data) {
        this.key = key;
    }

}
