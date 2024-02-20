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
        field.setLightOn(!field.isLightOn());
        if (field.isLightOn()) {
            field.getButton().getStyleClass().remove("lightIsOff");
            field.getButton().getStyleClass().add("lightIsOn");
        } else {
            field.getButton().getStyleClass().remove("lightIsOn");
            field.getButton().getStyleClass().add("lightIsOff");
        }

        int y = -2;
        int x = -2;

        //to chyba niepotrzebme
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                Field value = fields[i][j];
                if (value.getX() == field.getX() && value.getY() == field.getY()) {
                    y = field.getY();
                    x = field.getX();
                    break;
                }
            }
        }



        y = field.getY();
        x = field.getX();
        System.out.println("y: " + y);
        System.out.println("x: " + x);

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


        // zmieniamy kolor tych które trzeba

        // teraz jakos zmiejszyc indeks o jeden aby znalezc guzik odpowiedni z lewej jesli po lewo to true

        System.out.println("left: " + left);
        if(left){
            int yy = y;
            int xx = x - 1;
            System.out.println("yy: " + yy);
            System.out.println("xx: " + xx);
            Button b = fields[yy][xx].getButton();
            fields[yy][xx].setLightOn(!fields[yy][xx].isLightOn());
            if (fields[yy][xx].isLightOn()) {
                b.getStyleClass().remove("lightIsOff");
                b.getStyleClass().add("lightIsOn");
            } else {
                b.getStyleClass().remove("lightIsOn");
                b.getStyleClass().add("lightIsOff");
            }
        }

        if(right){
            int yy = y;
            int xx = x + 1;
            System.out.println("yy: " + yy);
            System.out.println("xx: " + xx);
            Button b = fields[yy][xx].getButton();
            fields[yy][xx].setLightOn(!fields[yy][xx].isLightOn());
            if (fields[yy][xx].isLightOn()) {
                b.getStyleClass().remove("lightIsOff");
                b.getStyleClass().add("lightIsOn");
            } else {
                b.getStyleClass().remove("lightIsOn");
                b.getStyleClass().add("lightIsOff");
            }
        }

        if(up){
            int yy = y - 1;
            int xx = x;
            System.out.println("yy: " + yy);
            System.out.println("xx: " + xx);
            Button b = fields[yy][xx].getButton();
            fields[yy][xx].setLightOn(!fields[yy][xx].isLightOn());
            if (fields[yy][xx].isLightOn()) {
                b.getStyleClass().remove("lightIsOff");
                b.getStyleClass().add("lightIsOn");
            } else {
                b.getStyleClass().remove("lightIsOn");
                b.getStyleClass().add("lightIsOff");
            }
        }
        if(down){
            int yy = y + 1;
            int xx = x;
            System.out.println("yy: " + yy);
            System.out.println("xx: " + xx);
            Button b = fields[yy][xx].getButton();
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

}
