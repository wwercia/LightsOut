package com.example.lightsout;

public class Field {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    private boolean isOn;

    public Field(int x, int y, boolean isOn){
        this.x = x;
        this.y = y;
        this.isOn = isOn;
    }

}
