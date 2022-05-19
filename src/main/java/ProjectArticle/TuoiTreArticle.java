package ProjectArticle;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.ArrayList;

public class TuoiTreArticle extends Application {
    public static ArrayList<Article> getListOfElementsInTT(String url, String category) throws IOException {
        final int MAX_ARTICLES = 50;
        ArrayList<Article> tuoitreArticleList = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        try {
            Elements articles;

            // Return articles from category Newest
            if (category.equals("Newest")) {
                articles = doc.select("div.box-focus.fl, ul.list-news-content li:not(:has(h3:contains(Video:)))");
                Elements titleAndLink = articles.select("h2.title-focus-title a, h3.title-news a");
                Elements thumbNail = articles.select("img");
                Elements description = articles.select("div.focus-first p.sapo, div.name-news p.sapo");

                int size = Math.min(titleAndLink.size(), MAX_ARTICLES);

                for (int i = 0; i < size; i++) {
                    // New article
                    tuoitreArticleList.add(new Article());
                    // Add title
                    tuoitreArticleList.get(i).setTitle(titleAndLink.get(i).text());
                    // Add source
                    tuoitreArticleList.get(i).setSource("TUOITRE.VN");
                    // Add category
                    tuoitreArticleList.get(i).setCategory(category);
                    // Add date&time
                    String date = Helper.timeToUnixString3(getTTArticleDate("http://tuoitre.vn" + titleAndLink.get(i).attr("href")));
                    tuoitreArticleList.get(i).setDate(Helper.unixToTime(date).substring(0, 16));
                    // Add time duration
                    tuoitreArticleList.get(i).setTimeDuration(Helper.timeDiff(date));
                    // Add thumbnail
                    if (thumbNail.get(i).hasAttr("data-src"))
                        tuoitreArticleList.get(i).setThumbnail(thumbNail.get(i).attr("abs:data-src"));
                    else {
                        if (thumbNail.get(i).hasAttr("src"))
                            tuoitreArticleList.get(i).setThumbnail(thumbNail.get(i).attr("abs:src"));
                    }
                    // Add description
                    tuoitreArticleList.get(i).setDescription(description.get(i).text());
                    // Add link to full article
                    tuoitreArticleList.get(i).setLinkToArticle("http://tuoitre.vn" + titleAndLink.get(i).attr("href"));
                }
            }

            // Return articles from other categories
            else {
                articles = doc.select("div.focus-top.clearfix, div.focus-first, div.news-first.clearfix, ul.list-news-content li:not(:has(h3:contains(Video:)))");
                Elements titleAndLink = articles.select("h2 a, div.name-news h3.title-news a");
                Elements thumbNail = articles.select("img");
                Elements descriptionOfArticle = articles.select("p.sapo");

                int size = Math.min(titleAndLink.size(), MAX_ARTICLES);

                for (int i = 0; i < size; i++) {
                    // New article
                    tuoitreArticleList.add(new Article());
                    // Add title
                    tuoitreArticleList.get(i).setTitle(titleAndLink.get(i).text());
                    // Add source
                    tuoitreArticleList.get(i).setSource("TUOITRE.VN");
                    // Add category
                    tuoitreArticleList.get(i).setPageCategory(category);
                    // Add date&time
                    String date = Helper.timeToUnixString3(getTTArticleDate("http://tuoitre.vn" + titleAndLink.get(i).attr("href")));
                    tuoitreArticleList.get(i).setDate(Helper.unixToTime(date).substring(0, 16));
                    // Add time duration
                    tuoitreArticleList.get(i).setTimeDuration(Helper.timeDiff(date));
                    // Add thumbnail
                    if (thumbNail.get(i).hasAttr("data-src"))
                        tuoitreArticleList.get(i).setThumbnail(thumbNail.get(i).attr("abs:data-src"));
                    else {
                        if (thumbNail.get(i).hasAttr("src"))
                            tuoitreArticleList.get(i).setThumbnail(thumbNail.get(i).attr("abs:src"));
                    }
                    // Add description
                    tuoitreArticleList.get(i).setDescription(descriptionOfArticle.get(i).text());
                    // Add link to full article
                    tuoitreArticleList.get(i).setLinkToArticle("http://tuoitre.vn" + titleAndLink.get(i).attr("href"));
                }
            }
        }
        catch (Selector.SelectorParseException e) {
            return null;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return tuoitreArticleList;
    }

    public static ArrayList<Article> getListOfSearchTTArticle(String keyword, String category) throws IOException {
        final int MAX_ARTICLES = 50;
        ArrayList<Article> listOfSearchArticle = new ArrayList<>();

        String convertedKeyWord = "https://tuoitre.vn/tim-kiem.htm?keywords=" + keyword.trim().replaceAll("\\s", "%20").toLowerCase();

        Document doc = Jsoup.connect(convertedKeyWord).get();

        try {
            Elements articles = doc.select("ul.list-news-content li:not(:has(h3:contains(Video)))");
            Elements titleAndLink = articles.select("h3.title-news a");
            Elements thumbNail = articles.select("img");
            Elements description = articles.select("p.sapo");

            int size = Math.min(titleAndLink.size(), MAX_ARTICLES);

            for (int i = 0; i < size; i++) {
                // Add article
                listOfSearchArticle.add(new Article());
                // Add title
                listOfSearchArticle.get(i).setTitle(titleAndLink.get(i).text());
                // Add source
                listOfSearchArticle.get(i).setSource("TUOITRE.VN");
                // Add category
                listOfSearchArticle.get(i).setCategory(category);
                // Add date&time
                String date = Helper.timeToUnixString3(getTTArticleDate("http://tuoitre.vn" + titleAndLink.get(i).attr("href")));
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
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return listOfSearchArticle;
    }

    public static String getTTArticleDate(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements articleDate = doc.select("section#content div.date-time");
        return articleDate.text().substring(0, 16);
    }

    public void displayTTArticle(Article a, VBox vbox) throws IOException {
        vbox.getChildren().clear();

        Document doc = Jsoup.connect(a.getLinkToArticle()).get();

        try {
            Elements content = doc.select("div.main-content-body");
            Elements body = content.select("div#main-detail-body.content.fck").select("> p, div.VCSortableInPreviewMode");
            Elements author = content.select("div.author");
            Elements pageCategory = doc.select("#content > div.content.w980 > div.title-content.clearfix.first > div > div > ul > li:nth-child(1)");

            // Add author
            a.setAuthor(author.text());
            // Add page category
            a.setPageCategory(pageCategory.text());

            // Display category
            // Normal texts
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
            Image logo = new Image("https://statictuoitre.mediacdn.vn/web_images/tto_default_avatar.png");
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
            // Normal texts
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
            // Normal texts
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
            // Normal texts
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

            // Display body
            for (Element part : body) {
                vbox.getChildren().add(skipLine(15));

                // Videos
                if (part.attr("type").equals("insertembedcode") || part.attr("type").equals("VideoStream")) {
                    // Normal texts
                    Text videoText = new Text("Watch video here: ");
                    videoText.setFill(Color.WHITE);
                    videoText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Hyperlink texts
                    String linkToVideo = part.attr("data-src");
                    Hyperlink videoHyperlink = new Hyperlink("link.");
                    videoHyperlink.setTextFill(Color.LIGHTPINK);
                    videoHyperlink.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
                    videoHyperlink.setUnderline(true);
                    // Set action for hyperlink
                    videoHyperlink.setOnAction(action -> getHostServices().showDocument(linkToVideo));
                    // Video caption normal texts
                    Text videoCaption = new Text(" (" + part.select("div.VideoCMS_Caption").text() + ")");
                    videoCaption.setFill(Color.WHITE);
                    videoCaption.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Text flow for all
                    TextFlow videoTextFlow = new TextFlow();
                    videoTextFlow.getChildren().addAll(videoText, videoHyperlink, videoCaption);
                    // Add all to VBox
                    vbox.getChildren().add(videoTextFlow);
                }

                // Images
                if (part.attr("type").equals("Photo")) {
                    // ImageViews
                    ImageView photoView = new ImageView();
                    photoView.setCache(true);
                    photoView.setCacheHint(CacheHint.SPEED);
                    photoView.setImage(new Image(part.select("img").attr("data-original"), 800, 0, true, true));
                    photoView.setPreserveRatio(true);

                    // Normal text for image caption
                    Text photoCaption = new Text("     " + part.select("div.PhotoCMS_Caption").text());
                    photoCaption.setFill(Color.WHITE);
                    photoCaption.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Add image view and image caption to VBox
                    vbox.getChildren().addAll(photoView, skipLine(1), photoCaption);
                }

                // Texts/Paragraphs
                if (part.hasText() && !part.attr("type").equals("RelatedOneNews") && !part.attr("type").equals("RelatedNews")) {
                    // Get all <p> that does not contain "Video:" or "Ảnh:"
                    Elements temp = part.select("p:not([data-placeholder])");
                    // Normal text for paragraphs
                    Text paragraph = new Text("     " + temp.text());
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
                }
            }
            vbox.getChildren().add(divider("-----/-----"));
            vbox.getChildren().add(skipLine(10));

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
            originalHyperLink.setOnAction(action -> getHostServices().showDocument(a.getLinkToArticle()));
            // Text flow for all
            TextFlow originalTextFlow = new TextFlow();
            originalTextFlow.getChildren().addAll(originalArticle, originalHyperLink);
            // Add all to VBox
            vbox.getChildren().add(originalTextFlow);

        }
        catch (Selector.SelectorParseException ignored) {
        }
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
    public void start(Stage primaryStage) {

    }
}
