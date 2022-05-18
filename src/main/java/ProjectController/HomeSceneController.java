package ProjectController;

import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeSceneController implements Initializable {
    public static ProgressBar progressBar = new ProgressBar();
    public static ProgressBar newestProgressBar = new ProgressBar();

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
