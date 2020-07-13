package com.servicematrix.javassist;

public class CoffeeMessage {
    private String id;

    private String message;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return this.id+"  "+this.message;
    }

}
