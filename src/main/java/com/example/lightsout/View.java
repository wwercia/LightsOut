package com.example.lightsout;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class View {

    private final VBox mainBox = new VBox();
    private Field[][] fields;


    public VBox initView() {

        mainBox.getStyleClass().add("main-box");
        mainBox.setAlignment(Pos.CENTER_RIGHT);

        Map map = new Map();
        try {
            map.loadMaps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Our random map:");
        fields = map.getNewMap();
        map.displayMap(fields);

        VBox boxForMap = new VBox(10);
        HBox box = new HBox(10);
        for (Field[] field : fields) {
            box.setAlignment(Pos.CENTER);
            for (Field value : field) {
                Button button = value.getButton();
                button.setOnAction(event -> changeButtonsColors(value));
                box.getChildren().add(button);
            }
            boxForMap.getChildren().add(box);
            box = new HBox(10);
        }
        mainBox.getChildren().add(boxForMap);
        return mainBox;
    }

    private void changeButtonsColors(Field field) {
        changeColorOfButton(field);
        int y = field.getY();
        int x = field.getX();
        // czy trzeba zmienic te przyciski czy wychodziłoby poza plansze
        // np jesli x to 0 to znaczy ze klikniety guzik jest z lewego brzegu a to oznacza ze po jego lewej nie ma guzika zadnego
        // czyli nie trzeba zmieniac guzika z lewej strony bo go nie ma
        boolean up = true;
        boolean left = true;
        boolean right = true;
        boolean down = true;
        if (y == 0){
            up = false;
        }
        if (y == 4){
            down = false;
        }
        if(x == 0){
            left = false;
        }
        if(x == 4){
            right = false;
        }
        changeColorOfButtonsAroundClickedButton(x, y, left, right, up, down);
    }

    private void changeColorOfButton(Field field){
        field.setLightOn(!field.isLightOn());
        if (field.isLightOn()) {
            field.getButton().getStyleClass().remove("lightIsOff");
            field.getButton().getStyleClass().add("lightIsOn");
        } else {
            field.getButton().getStyleClass().remove("lightIsOn");
            field.getButton().getStyleClass().add("lightIsOff");
        }
    }

    private void changeColorOfButtonsAroundClickedButton(int x, int y, boolean left, boolean right, boolean up, boolean down){
        if(left){
            int xx = x - 1;
            Button b = fields[y][xx].getButton();
            change(xx, y, b);
        }
        if(right){
            int xx = x + 1;
            Button b = fields[y][xx].getButton();
            change(xx, y, b);
        }
        if(up){
            int yy = y - 1;
            Button b = fields[yy][x].getButton();
            change(x, yy, b);
        }
        if(down){
            int yy = y + 1;
            Button b = fields[yy][x].getButton();
            change(x, yy, b);
        }
    }

    private void change(int xx, int yy, Button b){
        fields[yy][xx].setLightOn(!fields[yy][xx].isLightOn());
        if (fields[yy][xx].isLightOn()) {
            b.getStyleClass().remove("lightIsOff");
            b.getStyleClass().add("lightIsOn");
        } else {
            b.getStyleClass().remove("lightIsOn");
            b.getStyleClass().add("lightIsOff");
        }
    }
}
