module com.example.final_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens ProjectController to javafx.fxml;
    exports ProjectController;
}