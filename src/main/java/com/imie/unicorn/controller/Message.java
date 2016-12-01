package com.imie.unicorn.controller;

/**
 * Created by Yornletard on 01/12/2016.
 */
public class Message {

    private String key;
    private Object value;


    public Message(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
