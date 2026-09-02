module com.example.habittracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.habittracker to javafx.fxml;
    exports com.example.habittracker;
    exports com.example.habittracker.controller;
    opens com.example.habittracker.controller to javafx.fxml;
    exports com.example.habittracker.model;
    opens com.example.habittracker.model to javafx.fxml;

}