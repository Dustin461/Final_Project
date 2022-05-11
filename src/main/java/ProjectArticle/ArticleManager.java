package ProjectArticle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ArticleManager extends Application {

    public static ArrayList<ChangeListener<Number>> changeListeners = new ArrayList<>();

    public static TextFlow textFlow = new TextFlow();





    @Override
    public void start(Stage primaryStage) {

    }
}
