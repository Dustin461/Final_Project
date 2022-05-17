package ProjectArticle;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.ArrayList;

public class VnexpressArticle extends Application {
    public static ArrayList<Article> getListOfElementsInVnExpress(String url, String category) throws IOException {
        ArrayList<Article> VnExpressArticleList = new ArrayList<>();
        final int MAX_ARTICLE = 50;

        Document doc = Jsoup.connect(url).get();
        return VnExpressArticleList;

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
