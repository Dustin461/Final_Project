package ProjectArticle;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.io.IOException;
import java.util.ArrayList;

public class ThanhNienArticle extends Application {
    public static String getTNArticleTime(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements articleDate = doc.select("div.meta time");
        return articleDate.text().replace("- ", "");
    }

    public static String getTNArticleDescription(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements articleDescription = doc.select("div#chapeau.sapo.cms-desc");
        return articleDescription.text();
    }

    public static ArrayList<Article> getListOfElementsInTN(String url, String category) throws IOException {
        final int MAX_ARTICLES = 50;
        ArrayList<Article> thanhnienArticleList = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        try {
            Elements articles = doc.select("div.highlight, div.feature");
            Elements titleAndLink = articles.select("h2 a");
            Elements thumbNail = articles.select("a.story__thumb img");

            int size = Math.min(titleAndLink.size(), MAX_ARTICLES);

            for (int i = 0; i < size; i++) {
                // New article
                thanhnienArticleList.add(new Article());
                // Add title
                thanhnienArticleList.get(i).setTitle(titleAndLink.get(i).text());
                // Add source
                thanhnienArticleList.get(i).setSource("THANHNIEN.VN");
                // Add category
                thanhnienArticleList.get(i).setCategory(category);
                // Add date&time
                String date = Helper.timeToUnixString5(getTNArticleTime(titleAndLink.get(i).attr("href")));
                thanhnienArticleList.get(i).setDate(Helper.unixToTime(date).substring(0, 16));
                // Add time duration
                thanhnienArticleList.get(i).setTimeDuration(Helper.timeDiff(date));
                // Add thumbnail
                if (thumbNail.get(i).hasAttr("data-src"))
                    thanhnienArticleList.get(i).setThumbnail(thumbNail.get(i).attr("abs:data-src"));
                else {
                    if (thumbNail.get(i).hasAttr("src"))
                        thanhnienArticleList.get(i).setThumbnail(thumbNail.get(i).attr("abs:src"));
                }
                // Add description
                thanhnienArticleList.get(i).setDescription(getTNArticleDescription(titleAndLink.get(i).attr("href")));
                // Add link to article
                thanhnienArticleList.get(i).setLinkToArticle(titleAndLink.get(i).attr("href"));
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return thanhnienArticleList;
    }

    public static ArrayList<Article> getListOfSearchTNArticle(String keyword, String category) throws IOException {
        final int MAX_ARTICLES = 50;
        ArrayList<Article> listOfSearchArticle = new ArrayList<>();

        String convertedKeyword = "https://thanhnien.vn/tim-kiem/?q=" + keyword.trim().replaceAll("\\s", "%20").toLowerCase();

        Document doc = Jsoup.connect(convertedKeyword).get();
        try {
            Elements articles = doc.select("div.relative");
            Elements titleAndLink = articles.select("h2 a");
            Elements thumbNail = articles.select("a.story__thumb img");
            Elements description = articles.select("div.summary p");

            int size = Math.min(titleAndLink.size(), MAX_ARTICLES);

            for (int i = 0; i < size; i++) {
                // New Article
                listOfSearchArticle.add(new Article());
                // Add title
                listOfSearchArticle.get(i).setTitle(titleAndLink.get(i).text());
                // Add source
                listOfSearchArticle.get(i).setSource("THANHNIEN.VN");
                // Add category
                listOfSearchArticle.get(i).setCategory(category);
                // Add time&data
                String date = Helper.timeToUnixString5(getTNArticleTime(titleAndLink.get(i).attr("href")));
                listOfSearchArticle.get(i).setDate(Helper.unixToTime(date).substring(0, 16));
                // Add time duration
                listOfSearchArticle.get(i).setTimeDuration(Helper.timeDiff(date));
                // Add thumbnail
                if (thumbNail.get(i).hasAttr("data-src"))
                    listOfSearchArticle.get(i).setThumbnail(thumbNail.get(i).attr("abs:data-src"));
                else {
                    if (thumbNail.get(i).hasAttr("src"))
                        listOfSearchArticle.get(i).setThumbnail(thumbNail.get(i).attr("abs:src"));
                }
                // Add description
                listOfSearchArticle.get(i).setDescription(description.get(i).text());
                // Add link to article
                listOfSearchArticle.get(i).setLinkToArticle(titleAndLink.get(i).attr("href"));
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return listOfSearchArticle;
    }

    public void displayTNArticle(Article a, VBox vbox) throws IOException {
        vbox.getChildren().clear();

        Document doc = Jsoup.connect(a.getLinkToArticle()).get();

        try {
            Elements content = doc.select("div#abody.cms-body.detail"); // Content
            Elements body = content.select("p, table"); // p + image + video
            Elements author = doc.select("#storybox > div.details__author > div > div > h4 > a"); // author
            Elements pageCategory = doc.select
                    ("#st-container > div > div.site-content.media-content > div.l-grid > div.breadcrumbs > a:nth-child(2)"); // page category

            // Add author
            a.setAuthor(author.text());
            // Add page category
            a.setPageCategory(pageCategory.text());

            // Display category
            // Normal texts (Color: WHITE, Font: Helvetica, FontWeight: THIN, FontPosture: ITALIC, Size: 30)
            Text categoryText = new Text("---" + a.getCategory() + "---");
            categoryText.setFill(Color.WHITE);
            categoryText.setFont(Font.font("Helvetica", FontWeight.THIN, FontPosture.ITALIC, 30));
            // HBox for category
            HBox categoryHBox = new HBox(categoryText);
            categoryHBox.setAlignment(Pos.CENTER);
            // Add HBox to Vbox
            vbox.getChildren().add(categoryHBox);

            // Display page logo
            // Image & ImageView
            Image logo = new Image("https://amthanhxehoi.com/wp-content/uploads/2020/04/logo-baothanhnien.png");
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
            // Normal texts (Color: WHITE, Font: Helvetica, FontWeight: MEDIUM, FontPosture: REGULAR, Size: 20)
            Text cateAndDate = new Text("Category:  " + a.getPageCategory());
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
            // Normal texts (Color: LIGHTBLUE, Font: Verdana, FontWeight: EXTRA_BOLD, FontPosture: REGULAR, Size: 35)
            Text titleText = new Text(a.getTitle());
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

            // Display time & date & author
            // Normal texts (Color: WHITE, Font: Verdana, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
            Text timeAndDate = new Text("Date: " + a.getDate().replace(" ", "  -  ") + " (" + a.getTimeDuration() + ")\n"
                    + "Author: " + a.getAuthor());
            timeAndDate.setFill(Color.WHITE);
            timeAndDate.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 16));
            // HBox for time&date&author
            HBox timeAndDateHBox = new HBox(timeAndDate);
            timeAndDateHBox.setAlignment(Pos.BASELINE_LEFT);
            // Add HBox to VBox
            vbox.getChildren().add(timeAndDateHBox);
            vbox.getChildren().add(skipLine(15));

            // Display description
            // Normal texts (Color: GREY, Font: Verdana, FontWeight: BOLD, FontPosture: ITALIC, Size: 18)
            Text description = new Text(a.getDescription());
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
                vbox.getChildren().add(skipLine(15));

                if (part.hasClass("video")) {
                    // Normal texts (Color: WHITE, Font: Times New Roman, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
                    Text videoText = new Text("Watch video here: ");
                    videoText.setFill(Color.WHITE);
                    videoText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Hyperlink texts (Color: LIGHTPINK, Font: Times New Roman, FontWeight: BOLD, FontPosture: ITALIC, Size: 18)
                    String linkToVideo = part.select("div.clearfix.cms-video").attr("data-video-src");
                    Hyperlink videoHyperlink = new Hyperlink("link.");
                    videoHyperlink.setTextFill(Color.LIGHTPINK);
                    videoHyperlink.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
                    videoHyperlink.setUnderline(true);
                    // Set action for hyperlink
                    videoHyperlink.setOnAction(action -> getHostServices().showDocument(linkToVideo));
                    // Video caption normal texts (Color: WHITE, Font: Times New Roman, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
                    Text videoCaption = new Text(" (Caption: " + part.select("table.video td.caption").text() + ")");
                    videoCaption.setFill(Color.WHITE);
                    videoCaption.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Text flow for all
                    TextFlow videoTextFlow = new TextFlow();
                    videoTextFlow.getChildren().addAll(videoText, videoHyperlink, videoCaption);
                    // Add all to VBox
                    vbox.getChildren().add(videoTextFlow);
                }

                if (part.hasClass("picture")) {
                    Elements temp = part.select("td.pic img");
                    // ImageViews
                    ImageView photoView = new ImageView();
                    photoView.setCache(true);
                    photoView.setCacheHint(CacheHint.SPEED);
                    if (temp.hasAttr("data-src")) {
                        photoView.setImage(new Image(part.select("img").attr("data-src"), 800, 0, true, true));
                    }
                    else if (temp.hasAttr("src")) {
                        photoView.setImage(new Image(part.select("img").attr("src"), 800, 0, true, true));
                    }

                    // Normal text for image caption  (Color: WHITE, Font: Times New Roman, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
                    Text photoCaption = new Text("Caption: " + part.select("td.caption").text());
                    photoCaption.setFill(Color.WHITE);
                    photoCaption.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Text flow for image caption
                    TextFlow photoCaptionTextFow = new TextFlow(photoCaption);
                    // HBox for text flow
                    HBox photoCationHBox = new HBox(photoCaptionTextFow);
                    photoCationHBox.setAlignment(Pos.BASELINE_LEFT);
                    photoCationHBox.setMaxSize(1000, 1000);
                    // Add image view and image caption to VBox
                    vbox.getChildren().addAll(photoView, skipLine(1), photoCationHBox);
                }

                if (part.hasText() && !part.hasClass("picture") && !part.hasClass("video") && !part.parent().hasClass("caption") && !part.parent().hasClass("source")) {
                    // Normal text for paragraphs  (Color: WHITE, Font: Times New Roman, FontWeight: NORMAL, FontPosture: REGULAR, Size: 20)
                    Text paragraph = new Text("     " + part.text());
                    paragraph.setFill(Color.WHITE);
                    paragraph.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    // Text flow for paragraphs
                    TextFlow paragraphTextFlow = new TextFlow(paragraph);
                    // HBox for text flow
                    HBox paragraphHBox = new HBox(paragraphTextFlow);
                    paragraphHBox.setAlignment(Pos.BASELINE_LEFT);
                    paragraphHBox.setMaxSize(1000, 1000);
                    // Add to VBox
                    vbox.getChildren().add(paragraphHBox);
                }
            }

            vbox.getChildren().add(divider("-----/-----"));
            vbox.getChildren().add(skipLine(10));
        }
        catch (Selector.SelectorParseException ignored) {}
    }

    // Return an HBox that have a dividing string
    public static HBox divider(String str) {
        Text div = new Text(str);
        div.setFill(Color.WHITE);
        div.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30));
        HBox divHBox = new HBox(div);
        divHBox.setAlignment(Pos.CENTER);
        return divHBox;
    }

    // Return empty text to simulate 'enter'
    public static Text skipLine(double spacing) {
        Text emptyLine = new Text("");
        emptyLine.setFont(Font.font("Times New Roman", spacing));
        return emptyLine;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
