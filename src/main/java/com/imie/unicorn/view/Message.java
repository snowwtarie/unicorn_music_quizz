package com.imie.unicorn.view;

/**
 * Created by Stibo on 30/11/2016.
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
