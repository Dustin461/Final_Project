package ProjectController;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeSceneController implements Initializable {
    public static ProgressBar progressBar = new ProgressBar();
    public static ProgressBar newestProgressBar = new ProgressBar();

    @FXML
    private Button closeButton;
    @FXML
    private Button refreshButton;


    public void refreshButton(ActionEvent e){

    }

    public void closeButton(ActionEvent e){
        Platform.exit();
    }

    public HomeSceneController() {

        //Set the progress bar
        progressBar.setProgress(0.0);
        progressBar.setPrefHeight(5);
        progressBar.setPrefWidth(200);
        newestProgressBar.setProgress(0.0);
        newestProgressBar.setPrefHeight(5);
        newestProgressBar.setPrefWidth(200);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
