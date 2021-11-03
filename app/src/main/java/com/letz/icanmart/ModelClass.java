package com.letz.icanmart;

public class ModelClass
{
    String message;
    String from;

    public ModelClass() {

    }

    public ModelClass(String message, String from) {
        this.message = message;
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String list) {
        message = list;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
