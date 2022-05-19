package ProjectArticle;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class NhanDanArticle extends Application {

    private static HashMap<String, String> categoriesMap  = new HashMap<String, String>() {{
        put("Newest", "");
        put("Politics", "chinhtri");
        put("Business", "kinhte");
        put("Technology", "khoahoc-congnghe");
        put("Health", "y-te");
        put("Sports", "thethao");
        put("World", "thegioi");
        put("Entertainment", "");
        put("Others", "");
    }};

    public static void main(String[] args) throws IOException {
        ArrayList<Article> testList = getListOfElementsInNhanDan("https://nhandan.vn/vanhoa", "Others");
        testList.addAll(NhanDanArticle.getListOfElementsInNhanDan("https://nhandan.vn/xahoi", "Others"));
        testList.addAll(NhanDanArticle.getListOfElementsInNhanDan("https://nhandan.vn/du-lich", "Others"));
        testList.addAll(NhanDanArticle.getListOfElementsInNhanDan("https://nhandan.vn/giaoduc", "Others"));
        testList.addAll(NhanDanArticle.getListOfElementsInNhanDan("https://nhandan.vn/moi-truong", "Others"));
        System.out.println(testList);
        for (Article article:testList) {
            System.out.println(article.getDescription());
        }
        //launch(args);
    }

    public static ArrayList<Article> getListOfElementsInNhanDan(String url, String category) throws IOException{
        ArrayList<Article> nhanDanArticleList = new ArrayList<>();

        try {
            url += "/" + categoriesMap.get(category);
            Document doc = Jsoup.connect(url).get();
            //System.out.println(doc);
            Elements articles;
            articles = doc.select("div.boxlist-other article");
            Elements thumbnail = articles.select("div.box-img img");
            Elements title = articles.select("div.box-title a");
            Elements description = articles.select("div.box-des p");
            Elements date = articles.select("div.box-meta-small");

            for (int i = 0; i< articles.size(); i++){
                Article article = new Article();
                article.setTitle(title.get(i).text());
                article.setSource("NHANDAN.VN");
                article.setCategory(category);

                String dateStr = date.get(i).text();
                String dateUnix = Helper.timeToUnixString3Reverse(dateStr);
                article.setDate(dateUnix);

                article.setTimeDuration(Helper.timeDiff(dateUnix));

                article.setThumbnail(thumbnail.get(i).attr("data-src"));
                article.setLinkToArticle("https://nhandan.vn" + title.get(i).select("a").attr("href"));
                article.setDescription(description.get(i).text());

                article.setLinkToArticle("https://nhandan.vn" + title.get(i).select("a").attr("href"));

                nhanDanArticleList.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhanDanArticleList;
    }

    public static ArrayList<Article> getListOfSearchArticle(String keyword, String category) throws IOException{
        String url = "https://nhandan.vn/Search/" + keyword;
        ArrayList<Article> nhanDanSearchArticleList = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        //System.out.println(doc);

        try {
            Elements articles;
            articles = doc.select("div.boxlist-list article:has(div.box-img)");
            Elements thumbnail = articles.select("div.box-img img");
            Elements title = articles.select("div.box-title a");
            Elements description = articles.select("div.box-des p");
            Elements date = articles.select("div.box-meta-small");

            for (int i = 0; i< articles.size(); i++){
                Article article = new Article();
                article.setTitle(title.get(i).text());
                article.setSource("NHANDAN.VN");
                article.setCategory("Unknown");

                String dateStr = date.get(i).text();
                String dateUnix = Helper.timeToUnixString3Reverse(dateStr);
                article.setDate(dateUnix);

                article.setTimeDuration(Helper.timeDiff(dateUnix));

                article.setThumbnail(thumbnail.get(i).attr("data-src"));
                article.setLinkToArticle("https://nhandan.vn" + title.get(i).select("a").attr("href"));
                article.setDescription(description.get(i).text());

                article.setCategory(category);

                nhanDanSearchArticleList.add(article);
            }
        } catch (Exception e) {
            System.out.println(doc);
            e.printStackTrace();
        }

        return nhanDanSearchArticleList;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Testing
        ArrayList<Article> testList = getListOfElementsInNhanDan("https://nhandan.vn", "Newest");
        System.out.println(testList);
        Article testArticle = testList.get(4); //<--- Set article index here
        VBox articlePage = new VBox();
        displayNhanDanArticle(testArticle, articlePage);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(articlePage);
        scrollPane.setStyle("-fx-background: #000000; -fx-border-color: #000000;");
        Scene scene = new Scene(scrollPane, 1300, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void displayNhanDanArticle(Article article, VBox vbox) throws IOException {
        vbox.getChildren().clear();
        try {
            Document doc = Jsoup.connect(article.getLinkToArticle()).get();
            Elements content = doc.select("div.detail-content-body");
            Elements body = content.select("> p, div.light-img");
            String thumbnail = doc.select("div.box-detail-thumb").select("img").attr("src");
            Elements author = doc.select("div.box-author");

            article.setAuthor(author.select("strong").text());

            // Display page logo
            Image logo = new Image("https://img.nhandan.com.vn/Files/Images/2021/08/04/logoND-1628062143340.png");
            ImageView logoView = new ImageView();
            logoView.setCache(true);
            logoView.setCacheHint(CacheHint.SPEED);
            logoView.setImage(logo);
            logoView.setFitHeight(300);
            logoView.setFitWidth(700);
            logoView.setPreserveRatio(true);
            // HBox for logo view
            HBox logoViewHBox = new HBox(logoView);
            logoViewHBox.setAlignment(Pos.CENTER);
            // Add HBox to VBox
            vbox.getChildren().add(logoViewHBox);

            vbox.getChildren().add(skipLine(10));

            // Display page category
            // Normal texts
            Text cateAndDate = new Text("Category:  " + article.getCategory());
            cateAndDate.setFill(Color.WHITE);
            cateAndDate.setFont(Font.font("Helvetica", FontWeight.MEDIUM, FontPosture.REGULAR, 20));
            // HBox for page category
            HBox cateAndDateHBox = new HBox(cateAndDate);
            cateAndDateHBox.setAlignment(Pos.CENTER);
            // Add HBox to VBox
            vbox.getChildren().add(cateAndDateHBox);
            vbox.getChildren().add(divider("-----/-----"));
            vbox.getChildren().add(skipLine(10));

            // Display title
            // Normal texts
            Text titleText = new Text(article.getTitle());
            titleText.setFill(Color.LIGHTBLUE);
            titleText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 35));
            // Text flow for title
            TextFlow titleTextFlow = new TextFlow(titleText);
            titleTextFlow.setTextAlignment(TextAlignment.LEFT);
            // HBox for text flow
            HBox titleHBox = new HBox(titleTextFlow);
            titleHBox.setAlignment(Pos.BASELINE_LEFT);
            titleHBox.setMaxSize(1200, 1200);
            // Add HBox to VBox
            vbox.getChildren().add(titleHBox);

            //Display thumbnail
            ImageView thumbView = new ImageView();
            thumbView.setCache(true);
            thumbView.setCacheHint(CacheHint.SPEED);
            thumbView.setImage(new Image(thumbnail, 800, 0, true, true));
            thumbView.setPreserveRatio(true);

            vbox.getChildren().addAll(thumbView, skipLine(1));

            // Display time & date & author
            // Normal texts
            String date = new java.util.Date(Long.valueOf(article.getDate())*1000).toString();
            Text timeAndDate = new Text("Date: " + date + " (" + article.getTimeDuration() + ")\n" + "Author: " + article.getAuthor());
            timeAndDate.setFill(Color.WHITE);
            timeAndDate.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 16));
            // HBox for time&date&author
            HBox timeAndDateHBox = new HBox(timeAndDate);
            timeAndDateHBox.setAlignment(Pos.BASELINE_LEFT);
            // Add HBox to VBox
            vbox.getChildren().add(timeAndDateHBox);
            vbox.getChildren().add(skipLine(15));

            // Display description
            // Normal texts
            Text description = new Text(article.getDescription());
            description.setFill(Color.GREY);
            description.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 18));
            // Text flow for description
            TextFlow descriptionTextFlow = new TextFlow(description);
            descriptionTextFlow.setTextAlignment(TextAlignment.LEFT);
            // HBox for description
            HBox descriptionHBox = new HBox(descriptionTextFlow);
            descriptionHBox.setAlignment(Pos.BASELINE_LEFT);
            descriptionHBox.setMaxSize(1000, 1000);
            // Add HBox to Vbox
            vbox.getChildren().add(descriptionHBox);

            for (Element part : body) {
                // Images
                if (part.attr("class").equals("light-img")) {
                    System.out.println(part.select("img").attr("src"));
                    // ImageViews
                    ImageView photoView = new ImageView();
                    photoView.setCache(true);
                    photoView.setCacheHint(CacheHint.SPEED);
                    photoView.setImage(new Image(part.select("img").attr("src"), 800, 0, true, true));
                    photoView.setPreserveRatio(true);

                    // Normal text for image caption
                    Text photoCaption = new Text("     " + part.select("figcaption.img-cap").select("em").text());
                    photoCaption.setFill(Color.WHITE);
                    photoCaption.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Add image view and image caption to VBox
                    vbox.getChildren().addAll(photoView, skipLine(1), photoCaption);
                }

                if(part.hasText()) {
                    Text paragraph = new Text(part.text());
                    paragraph.setFill(Color.WHITE);
                    paragraph.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 18));
                    // Text flow for paragraphs
                    TextFlow paragraphTextFlow = new TextFlow(paragraph);
                    // HBox for text flow
                    HBox paragraphHBox = new HBox(paragraphTextFlow);
                    paragraphHBox.setAlignment(Pos.BASELINE_LEFT);
                    paragraphHBox.setMaxSize(1000, 1000);
                    // Add to VBox
                    vbox.getChildren().add(paragraphHBox);
                    vbox.getChildren().add(skipLine(10));
                }
            }

            // Link to full article
            // Normal texts
            Text originalArticle = new Text("To original post here: ");
            originalArticle.setFill(Color.WHITE);
            originalArticle.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 18));
            // Hyperlink texts
            Hyperlink originalHyperLink = new Hyperlink("link.");
            originalHyperLink.setTextFill(Color.LIGHTPINK);
            originalHyperLink.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
            originalHyperLink.setUnderline(true);
            // Set action for hyperlink
            originalHyperLink.setOnAction(action -> {
                getHostServices().showDocument(article.getLinkToArticle());
            });
            // Text flow for all
            TextFlow originalTextFlow = new TextFlow();
            originalTextFlow.getChildren().addAll(originalArticle, originalHyperLink);
            // Add all to VBox
            vbox.getChildren().add(originalTextFlow);
        } catch (Exception e) {}

    }

    public static Text skipLine(double spacing) {
        Text emptyLine = new Text("");
        emptyLine.setFont(Font.font("Times New Roman", spacing));
        return emptyLine;
    }

    public static HBox divider(String str) {
        Text div = new Text(str);
        div.setFill(Color.WHITE);
        div.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
        HBox divHBox = new HBox(div);
        divHBox.setAlignment(Pos.CENTER);
        return divHBox;
    }
}
