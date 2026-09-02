module com.example.habittracker {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.habittracker;
    exports com.example.habittracker.model;
    exports com.example.habittracker.controller;

    opens com.example.habittracker to javafx.fxml;
    opens com.example.habittracker.controller to javafx.fxml;
}