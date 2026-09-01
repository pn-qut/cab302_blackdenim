module com.example.habittracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.habittracker to javafx.fxml;
    opens com.example.habittracker.controller to javafx.fxml;
    exports com.example.habittracker;

}