package ProjectController;

import ProjectArticle.Article;
import ProjectArticle.ArticleList;
import ProjectController.LayoutController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;




import java.net.URL;
import java.util.ResourceBundle;

public class HomeSceneController implements Initializable {
    @FXML
    private Button BusinessButton;

    @FXML
    private Button CovidButton;

    @FXML
    private Button EntertainmentButton;

    @FXML
    private Button HealthButton;

    @FXML
    private Button NewestButton;

    @FXML
    private Button OthersButton;

    @FXML
    private Button PoliticsButton;

    @FXML
    private Button SportButton;

    @FXML
    private Button WorldButton;

    @FXML
    private BorderPane borderPaneUnderScrollPane;

    @FXML
    private Button closeButton;

    @FXML
    private Button refreshButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button technologyButton;

    @FXML
    void closeButton(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void refreshButton(ActionEvent event) {

    }

    public String searchText;
    public static ProgressBar progressBar = new ProgressBar();
    public static ProgressBar newestProgressBar = new ProgressBar();

    // Pagination control
    public int currentCategoryIndex = 0, currentArticleIndex = 0;
    public ArrayList<Article> currentCategoryList;
    public Pagination currentPagination;

    // Instance Layout controller
    public LayoutController layoutController;

    //Vbox to display layout
    public VBox displayLayoutVbox = new VBox();

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
