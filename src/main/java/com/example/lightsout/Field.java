package com.example.lightsout;

import javafx.scene.control.Button;

public class Field {

    private Button button;
    private int x;
    private int y;
    private boolean isLightOn;

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

    public boolean isLightOn() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        isLightOn = lightOn;
    }
    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Field(int y, int x, boolean isOn, Button button){
        this.x = x;
        this.y = y;
        this.isLightOn = isOn;
        button.setPrefSize(60,60);
        if(isOn){
            button.getStyleClass().add("lightIsOn");
        }else {
            button.getStyleClass().add("lightIsOff");
        }
        this.button = button;
    }

}
