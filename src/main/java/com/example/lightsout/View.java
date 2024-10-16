package com.example.lightsout;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class View {

    private final VBox mainBox = new VBox(0);
    private VBox boxForMap;
    private Field[][] fields = new Field[5][5];
    private Map map;
    //private Map previousMap = new Map();

    private Field[][] startCombination;
    private int numberOfMoves = 0;



    private int[][] actualMap = new int[5][5];
    private int[][] previousMap = new int[5][5];


    // to pole potrzebne aby wiedziec czy jest to pierwszy raz odpalana metodai nie ma jeszcze previousMap
    private int count = 0;
    public VBox initView() {

        mainBox.getStyleClass().add("main-box");
        mainBox.setAlignment(Pos.CENTER_RIGHT);

        map = new Map();
        try {
            map.loadMaps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HBox boxForButtons = new HBox(5);
        boxForButtons.setAlignment(Pos.CENTER);
        Button restartFieldsButton = new Button("clear all");
        restartFieldsButton.getStyleClass().add("play-again-button");
        Button changeMapButton = new Button("new game");
        changeMapButton.getStyleClass().add("play-again-button");
        restartFieldsButton.setStyle("-fx-font-size: 13px;");
        changeMapButton.setStyle("-fx-font-size: 13px;");
        boxForButtons.getChildren().addAll(restartFieldsButton, changeMapButton);

        HBox boxForInfoImage = new HBox();
        boxForInfoImage.setAlignment(Pos.BASELINE_RIGHT);

        Button buttonforImage = new Button("?");
        buttonforImage.getStyleClass().add("play-again-button");
        buttonforImage.setStyle("-fx-font-size: 13px;");
        buttonforImage.setOnAction(event -> showImagewindow());
        boxForInfoImage.getChildren().add(buttonforImage);

        restartFieldsButton.setOnAction(event -> moveAllFieldsToStart());
        changeMapButton.setOnAction(event -> changeMap());

        fields = map.getNewMap();

        actualMap = map.getMap0And1(fields);
        if(count == 0){
            previousMap = actualMap;
        }else {

            boolean isTheSame = true;
            for (int i = 0; i < actualMap.length; i++) {
                for (int j = 0; j < actualMap[i].length; j++) {
                    if(actualMap[i][j] != previousMap[i][j]){
                        isTheSame = false;
                    }
                }
            }

            while (isTheSame){
                fields = map.getNewMap();
                actualMap = map.getMap0And1(fields);
                isTheSame = true;
                for (int i = 0; i < actualMap.length; i++) {
                    for (int j = 0; j < actualMap[i].length; j++) {
                        if(actualMap[i][j] != previousMap[i][j]){
                            isTheSame = false;
                        }
                    }
                }
                System.out.println("losuje ponownie bo była ta sama:");
                map.displayMap(fields);
                previousMap = actualMap;
            }
            actualMap = map.getMap0And1(fields);
            previousMap = actualMap;

        }
        count++;


        startCombination = new Field[5][5];
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                Field value = fields[i][j];
                Field newField = new Field(value.getX(), value.getY(), value.isLightOn(), new Button());
                startCombination[i][j] = newField;
            }
        }


        //map.displayMap(fields);
        numberOfMoves = 0;

        boxForMap = new VBox(10);
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
        mainBox.getChildren().addAll(boxForButtons, boxForInfoImage, boxForMap);
        return mainBox;
    }

    private void showImagewindow(){
        Stage optionsStage = new Stage();
        //optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("hint");

        VBox main = new VBox(10);
        main.setAlignment(Pos.CENTER);
        main.getStyleClass().add("main-box");

        File file = new File("C:\\Users\\wwerc\\IdeaProjects\\LightsOut\\src\\main\\resources\\com\\example\\lightsout\\hint.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);

        main.getChildren().add(imageView);
        Scene optionsScene = new Scene(main, 450, 350);
        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsStage.setScene(optionsScene);
        optionsStage.show();
    }

    private void changeMap() {
        mainBox.getChildren().clear();
        initView();
    }

    private void moveAllFieldsToStart() {
        mainBox.getChildren().remove(boxForMap);
        numberOfMoves = 0;
        System.out.println();
        map.displayMap(fields);
        map.displayMap(startCombination);
        boxForMap = new VBox(10);
        HBox box = new HBox(10);
        for (int i = 0; i < startCombination.length; i++) {
            box.setAlignment(Pos.CENTER);
            for (int j = 0; j < fields[i].length; j++) {
                Field oldValue = fields[i][j];
                Field startProps = startCombination[i][j];
                if (oldValue.isLightOn() != startProps.isLightOn()) {
                    if (startProps.isLightOn()) {
                        oldValue.getButton().getStyleClass().remove("lightIsOff");
                        oldValue.getButton().getStyleClass().add("lightIsOn");
                    } else {
                        oldValue.getButton().getStyleClass().remove("lightIsOn");
                        oldValue.getButton().getStyleClass().add("lightIsOff");
                    }

                }
                oldValue.setLightOn(startProps.isLightOn());
                box.getChildren().add(oldValue.getButton());
            }
            boxForMap.getChildren().add(box);
            box = new HBox(10);
        }
        mainBox.getChildren().add(boxForMap);
    }

    private void changeButtonsColors(Field field) {
        numberOfMoves++;
        changeColorOfButton(field);
        int y = field.getY();
        int x = field.getX();
        // sprawdzam czy trzeba zmienic te przyciski czy wychodziłoby poza plansze
        // np jeśli x to 0 to znaczy ze klikniety guzik jest z lewego brzegu a to oznacza ze po jego lewej nie ma guzika żadnego
        // czyli nie trzeba zmieniać guzika z lewej strony bo go nie ma
        boolean up = true;
        boolean left = true;
        boolean right = true;
        boolean down = true;
        if (y == 0) {
            up = false;
        }
        if (y == 4) {
            down = false;
        }
        if (x == 0) {
            left = false;
        }
        if (x == 4) {
            right = false;
        }
        changeColorOfButtonsAroundClickedButton(x, y, left, right, up, down);
        checkIfEveryLightIsOff();
    }

    private void changeColorOfButton(Field field) {
        field.setLightOn(!field.isLightOn());
        if (field.isLightOn()) {
            field.getButton().getStyleClass().remove("lightIsOff");
            field.getButton().getStyleClass().add("lightIsOn");
        } else {
            field.getButton().getStyleClass().remove("lightIsOn");
            field.getButton().getStyleClass().add("lightIsOff");
        }
    }

    private void changeColorOfButtonsAroundClickedButton(int x, int y, boolean left, boolean right, boolean up, boolean down) {
        if (left) {
            int xx = x - 1;
            Button b = fields[y][xx].getButton();
            change(xx, y, b);
        }
        if (right) {
            int xx = x + 1;
            Button b = fields[y][xx].getButton();
            change(xx, y, b);
        }
        if (up) {
            int yy = y - 1;
            Button b = fields[yy][x].getButton();
            change(x, yy, b);
        }
        if (down) {
            int yy = y + 1;
            Button b = fields[yy][x].getButton();
            change(x, yy, b);
        }
    }

    private void change(int xx, int yy, Button b) {
        fields[yy][xx].setLightOn(!fields[yy][xx].isLightOn());
        if (fields[yy][xx].isLightOn()) {
            b.getStyleClass().remove("lightIsOff");
            b.getStyleClass().add("lightIsOn");
        } else {
            b.getStyleClass().remove("lightIsOn");
            b.getStyleClass().add("lightIsOff");
        }
    }

    private void checkIfEveryLightIsOff() {
        boolean areAllLightsOff = true;
        for (Field[] field : fields) {
            for (Field value : field) {
                if (value.isLightOn()) {
                    areAllLightsOff = false;
                    break;
                }
            }
        }
        if (areAllLightsOff) {
            System.out.println("Gratulacje wyłączyłaś/eś wszystkie światła! :)");
            System.out.println();
            windowAfterWining();
        }
    }

    private void windowAfterWining() {

        Stage optionsStage = new Stage();
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("You won!");

        VBox main = new VBox(10);
        main.setAlignment(Pos.CENTER);
        main.getStyleClass().add("main-box");

        Label congratulationsWord = new Label("Congratulations!");
        Label youWonWord = new Label("You won!");
        Label movesWord = new Label("Moves: " + numberOfMoves);
        congratulationsWord.getStyleClass().add("text");
        youWonWord.getStyleClass().add("text");
        movesWord.getStyleClass().add("text");
        Button playAgainButton = new Button("Play again");
        playAgainButton.getStyleClass().add("play-again-button");

        playAgainButton.setOnAction(actionEvent -> {
            mainBox.getChildren().clear();
            initView();
            optionsStage.close();
        });

        main.getChildren().addAll(congratulationsWord, youWonWord, movesWord, playAgainButton);
        Scene optionsScene = new Scene(main, 300, 200);
        optionsScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        optionsStage.setScene(optionsScene);
        optionsStage.showAndWait();

    }
}
