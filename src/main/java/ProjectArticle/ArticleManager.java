package ProjectArticle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;



public class ArticleManager extends Application {
    public static ArrayList<ChangeListener<Number>> changeListeners = new ArrayList<>();
    public static TextFlow textFlow = new TextFlow();


    @Override
    public void start(Stage primaryStage) {

    }
}
