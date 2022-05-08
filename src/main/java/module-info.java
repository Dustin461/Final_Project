module com.example.final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ProjectController to javafx.fxml;
    exports ProjectController;
}