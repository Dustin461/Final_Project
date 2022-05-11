module com.example.final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jsoup;


    opens ProjectController to javafx.fxml;
    exports ProjectController;
}