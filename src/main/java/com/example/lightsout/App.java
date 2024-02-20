package com.example.lightsout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        View view = new View();
        VBox root = view.initView();
        Scene scene = new Scene(root, 500, 450);
        stage.setTitle("Planning app");
        stage.setScene(scene);
        stage.show();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
    }
}
