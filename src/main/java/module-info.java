module com.example.lightsout {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lightsout to javafx.fxml;
    exports com.example.lightsout;
}