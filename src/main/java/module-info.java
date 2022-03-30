module com.example.groupworkgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;


    opens com.example.groupworkgame to javafx.fxml;
    exports com.example.groupworkgame;
}