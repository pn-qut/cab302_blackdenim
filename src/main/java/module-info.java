module com.example.habittracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.habittracker to javafx.fxml;
    opens com.example.habittracker.controller to javafx.fxml;
    opens com.example.habittracker.model to javafx.fxml;
    exports com.example.habittracker;
    exports com.example.habittracker.controller;
    exports com.example.habittracker.model;

}