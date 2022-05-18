package ProjectArticle;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import javafx.scene.text.*;

import java.io.IOException;
import java.util.ArrayList;

public class VnExpressArticle extends Application {
    /** Get the list of the newest article with RSS **/
    public static ArrayList<Article> getVnExpressArticleNewest(String url, String category) throws IOException {
        ArrayList<Article> VnExpressNewestList = new ArrayList<>();
        final int MAX_ARTICLE = 50;
        Document doc = Jsoup.connect(url).get();
        Elements articles = doc.select("item");

        // Eliminate the articles do not have thumbnail
        ArrayList<Element> removeArticle = new ArrayList<>();
        for (Element index : articles) {
            if (!index.select("description").text().contains("img")) {
                removeArticle.add(index);
                continue;
            }
            //Extract thumbnail from the description
            String description = index.select("description").text();
            int startOfLink = description.indexOf("\"");
            int endOfLink = description.indexOf("\"", startOfLink + 1);
            int startOfThumbnail = description.indexOf("\"", endOfLink + 1);
            int endOfThumbnail = description.indexOf("\"", startOfThumbnail + 1);
            // Set thumb for object
            if (endOfThumbnail <= 2) {
                removeArticle.add(index);
            }
        }
        articles.removeAll(removeArticle);
        removeArticle.clear();

        try{
            for (int i = 0; i < MAX_ARTICLE; i++) {
                VnExpressNewestList.add(new Article());

                //Extract thumbnail from the description
                String description = articles.get(i).select("description").text();
                int startOfLink = description.indexOf("\"");
                int endOfLink = description.indexOf("\"", startOfLink + 1);
                int startOfThumbnail = description.indexOf("\"", endOfLink + 1);
                int endOfThumbnail = description.indexOf("\"", startOfThumbnail + 1);
                //Set thumbnail for each article
                VnExpressNewestList.get(i).setThumbnail(description.substring(startOfThumbnail + 1, endOfThumbnail));
                //Set title for each article
                VnExpressNewestList.get(i).setTitle(articles.get(i).select("title").text());
                //Set date for each article
                VnExpressNewestList.get(i).setDate(Helper.timeToUnixString2(articles.get(i).select("pubDate").text()));
                //Set source for each article
                VnExpressNewestList.get(i).setSource("VnExpress");
                //Set category for each article
                VnExpressNewestList.get(i).setCategory(category);
                //Set time duration for each article
                VnExpressNewestList.get(i).setTimeDuration(Helper.timeDiff(VnExpressNewestList.get(i).getDate()));
                //Set link for each article
                VnExpressNewestList.get(i).setLinkToArticle(articles.get(i).select("link").text());
            }
        } catch (Selector.SelectorParseException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            VnExpressNewestList.remove(VnExpressNewestList.size() - 1);
        }
        return VnExpressNewestList;
    }

    /** Get the list of other articles **/
    public static ArrayList<Article> getVnExpressArticleList(String url, String category) throws IOException {
        ArrayList<Article> VnExpressList = new ArrayList<>();
        final int MAX_ARTICLE = 50;
        Document doc = Jsoup.connect(url).get();
        Elements articles = doc.select("article.item-news.item-news-common");
        Elements titleOfArticle = doc.select("h2.title-news a[title]");
        Elements thumbnailOfArticle = doc.select("div.thumb-art img[itemprop]");

        try {
            for (int i = 0; i < MAX_ARTICLE; i++) {
                VnExpressList.add(new Article());
                //Set source of each article
                VnExpressList.get(i).setSource("VnExpress");
                //Set category of each article
                VnExpressList.get(i).setCategory(category);
                //Set title of each article
                VnExpressList.get(i).setTitle(titleOfArticle.get(i).attr("title"));
                //Set thumbnail of each article
                VnExpressList.get(i).setThumbnail(thumbnailOfArticle.get(i).getElementsByTag("img").attr("data:src"));
                //Set link to each article
                VnExpressList.get(i).setLinkToArticle(articles.select("div.thumb-art a[href]").get(i).attr("abs:href"));
            }

        }catch (Selector.SelectorParseException e) {
            return null;
        }catch (IndexOutOfBoundsException e) {
            VnExpressList.remove(VnExpressList.size() - 1);
        }
        return VnExpressList;
    }

    /** Get the list of searched article **/
    public static ArrayList<Article> getListOfSearchVEArticle(String keyWord, String category) throws IOException {
        ArrayList<Article> listOfSearchArticle = new ArrayList<>();

        //Convert the keyword to an url with format: https://timkiem.vnexpress.net/?q=content%20content%20
        String convertedLink = "https://timkiem.vnexpress.net/?q=" + keyWord.trim().replaceAll("\\s", "%20").toLowerCase();

        //Set up Jsoup to scrap the data
        Document doc = Jsoup.connect(convertedLink).get();
        Elements articles = doc.select("div.width_common.list-news-subfolder");
        Elements thumbArt = doc.select("div.thumb-art");

        // Eliminate the elements do not have thumbnail
        ArrayList<Element> removeArticle = new ArrayList<>();
        for (Element i : articles) {
            // Set thumb for each object
            if (!i.select("div.thumb-art picture img").hasAttr("data-src")) {
                removeArticle.add(i);
            }
        }
        articles.removeAll(removeArticle);
        removeArticle.clear();

        int amountArticle = Math.min(articles.size(), 50);
        try {
            for (int j = 0; j < amountArticle; j++) {
                listOfSearchArticle.add(new Article());
                //Set source of each article
                listOfSearchArticle.get(j).setSource("VnExpress");
                //Set category of each article
                listOfSearchArticle.get(j).setCategory(category);
                //Set title of each article
                listOfSearchArticle.get(j).setTitle(thumbArt.get(j).select("a").attr("title"));
                //Set thumbnail of each article
                listOfSearchArticle.get(j).setThumbnail(thumbArt.get(j).select("picture img").attr("data-src"));
            }

        }catch (Selector.SelectorParseException e) {
            return null;
        }catch (IndexOutOfBoundsException e) {
            listOfSearchArticle.remove(listOfSearchArticle.size() - 1);
        }

        return listOfSearchArticle;
    }

    /** Display article **/
    public void displayVEArticle(Article a, VBox vBox) throws IOException {
        vBox.getChildren().clear();
        String url = a.getLinkToArticle();
        Document doc = Jsoup.connect(url).get();

        try{
            //Select all elements of the article
            Elements article = doc.select("div.container");
            //Select description of article
            Elements description = article.select("p.description");
            //Select all content in the body of article
            Elements body = article.select("article.fcl_detail p, img[data-src], source[data-srcset, figcaption, p.author_mail, div.videoContainer");
            //Select category of article
            Elements pageCategory = article.select("ul.breadcrumb li");
            //Select time of article
            Elements time = article.select("span.date");
            //Select audio of article
            Elements audio = doc.select("div.audioContainer");

            //Set date of the article
            a.setDate(time.text());

            //Set category of the article
            a.setPageCategory("");
            int i = 0;
            for(Element category : pageCategory) {
                if(i != pageCategory.size() - 1) {
                    a.setPageCategory(a.getPageCategory() + category.text() + "-");
                }else {
                    a.setPageCategory(a.getPageCategory() + category.text());
                }
                i++;
            }
            //Display page logo
            Image logo = new Image("image/vnexpress_logo_big.png",200,200,true,true,true);
            ImageView logoView = new ImageView();
            logoView.setCache(true);
            logoView.setCacheHint(CacheHint.SPEED);
            logoView.setImage(logo);
            logoView.setFitHeight(60);
            logoView.setPreserveRatio(true);
            // HBox for logo view
            HBox logoViewHBox = new HBox(logoView);
            logoViewHBox.setAlignment(Pos.CENTER);
            // Add HBox to VBox
            vBox.getChildren().add(logoViewHBox);
            vBox.getChildren().add(skipLine(10));

            //Display category
            Text categoryText = new Text(a.getCategory());
            categoryText.setFill(Color.BLACK);
            categoryText.setFont(Font.font("Helvetica", FontWeight.BOLD, 46));
            //HBox for category
            HBox categoryHBox = new HBox(categoryText);
            categoryHBox.setAlignment(Pos.TOP_LEFT);
            //Add HBox to VBox
            vBox.getChildren().add(categoryHBox);

            //Display original category and date of the article on the website
            Text textPageCategory = new Text(a.getPageCategory() + "  " + a.getDate());
            textPageCategory.setFill(Color.BLACK);
            textPageCategory.setFont(Font.font("Helvetica", FontWeight.MEDIUM, FontPosture.REGULAR, 20));
            // HBox for page category
            HBox pageCategoryHBox = new HBox(textPageCategory);
            pageCategoryHBox.setAlignment(Pos.CENTER);
            // Add HBox to VBox
            vBox.getChildren().add(pageCategoryHBox);
            vBox.getChildren().add(skipLine(10));

            //Display title
            Text titleText = new Text(a.getTitle());
            titleText.setFill(Color.BLACK);
            titleText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 36));
            // Text flow for title
            TextFlow titleTextFlow = new TextFlow(titleText);
            titleTextFlow.setTextAlignment(TextAlignment.JUSTIFY);
            // HBox for text flow
            HBox titleHBox = new HBox(titleTextFlow);
            titleHBox.setAlignment(Pos.BASELINE_LEFT);
            titleHBox.setMaxSize(1200, 1200);
            // Add HBox to VBox
            vBox.getChildren().add(titleHBox);

            //Display description
            if(description.select("span.location-stamp").hasText()) {
                a.setDescription(description.select("span.location-stamp").text());
            }else {
                a.setDescription(description.text());
            }
            Text descriptionText = new Text(a.getDescription());
            descriptionText.setFill(Color.BLACK);
            descriptionText.setFont(Font.font("Time New Roman", FontWeight.MEDIUM, FontPosture.ITALIC, 18));
            // Text flow for description
            TextFlow descriptionTextFlow = new TextFlow(descriptionText);
            descriptionTextFlow.setTextAlignment(TextAlignment.JUSTIFY);
            // HBox for description
            HBox descriptionHBox = new HBox(descriptionTextFlow);
            descriptionHBox.setAlignment(Pos.BASELINE_LEFT);
            descriptionHBox.setMaxSize(1000, 1000);
            // Add HBox to Vbox
            vBox.getChildren().add(descriptionHBox);


            //Display audio
            if (audio.select("audio").hasAttr("src") || audio.select("audio").hasAttr("data-src")) {
                // Normal texts (Color: WHITE, Font: Times New Roman, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
                Text audioText = new Text("Listen audio here: ");
                audioText.setFill(Color.BLACK);
                audioText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                // Hyperlink texts (Color: LIGHTPINK, Font: Times New Roman, FontWeight: BOLD, FontPosture: ITALIC, Size: 18)
                Hyperlink audioHyperlink = new Hyperlink("link.");
                audioHyperlink.setTextFill(Color.LIGHTPINK);
                audioHyperlink.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
                audioHyperlink.setUnderline(true);
                // Set action for hyperlink
                audioHyperlink.setOnAction(action -> getHostServices().showDocument(a.getLinkToArticle()));
                // Text flow for all
                TextFlow audioTextFlow = new TextFlow();
                audioTextFlow.getChildren().addAll(audioText, audioHyperlink);
                // Add all to VBox
                vBox.getChildren().add(audioTextFlow);

            }
            //Display all content in the body
            for (Element part : body) {
                vBox.getChildren().add(skipLine(15));

                //Display video
                if (part.hasClass("videoContainer")) {
                    // Normal texts (Color: WHITE, Font: Times New Roman, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
                    Text videoText = new Text("Watch video here: ");
                    videoText.setFill(Color.BLACK);
                    videoText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Hyperlink texts (Color: LIGHTPINK, Font: Times New Roman, FontWeight: BOLD, FontPosture: ITALIC, Size: 18)
                    Hyperlink videoHyperlink = new Hyperlink("link.");
                    videoHyperlink.setTextFill(Color.LIGHTPINK);
                    videoHyperlink.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
                    videoHyperlink.setUnderline(true);
                    // Set action for hyperlink
                    videoHyperlink.setOnAction(action -> getHostServices().showDocument(a.getLinkToArticle()));
                    // Video caption normal texts (Color: WHITE, Font: Times New Roman, FontWeight: BOLD, FontPosture: REGULAR, Size: 16)
                    Text videoCaption = new Text(" (Caption: " + part.select("table.video td.caption").text() + ")");
                    videoCaption.setFill(Color.BLACK);
                    videoCaption.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Text flow for all
                    TextFlow videoTextFlow = new TextFlow();
                    videoTextFlow.getChildren().addAll(videoText, videoHyperlink, videoCaption);
                    // Add all to VBox
                    vBox.getChildren().add(videoTextFlow);
                }

                //Display image
                if (part.hasClass("fig-picture")) {
                    Elements temp = part.select("img");
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
                    photoCaption.setFill(Color.BLACK);
                    photoCaption.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 16));
                    // Text flow for image caption
                    TextFlow photoCaptionTextFow = new TextFlow(photoCaption);
                    // HBox for text flow
                    HBox photoCationHBox = new HBox(photoCaptionTextFow);
                    photoCationHBox.setAlignment(Pos.BASELINE_LEFT);
                    photoCationHBox.setMaxSize(1000, 1000);
                    // Add image view and image caption to VBox
                    vBox.getChildren().addAll(photoView, skipLine(1), photoCationHBox);
                }

                //Display text/paragraph
                if (part.hasText() && !part.hasClass("fig-picture") && !part.hasClass("videoContainer")) {
                    // Normal text for paragraphs  (Color: WHITE, Font: Times New Roman, FontWeight: NORMAL, FontPosture: REGULAR, Size: 18)
                    Text paragraph = new Text(part.text());
                    paragraph.setFill(Color.BLACK);
                    paragraph.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 18));
                    // Text flow for paragraphs
                    TextFlow paragraphTextFlow = new TextFlow(paragraph);
                    paragraphTextFlow.setTextAlignment(TextAlignment.JUSTIFY);
                    // HBox for text flow
                    HBox paragraphHBox = new HBox(paragraphTextFlow);
                    paragraphHBox.setAlignment(Pos.BASELINE_LEFT);
                    paragraphHBox.setMaxSize(1000, 1000);
                    // Add to VBox
                    vBox.getChildren().add(paragraphHBox);
                }

                //Add and display author
                //First style of author
                if(part.attr("style").contains("right") || part.hasAttr("text-align")) {
                    Text authorStyle1 = new Text();
                    authorStyle1.setFill(Color.BLACK);
                    authorStyle1.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
                    // Text flow for paragraphs
                    TextFlow author1TextFlow = new TextFlow(authorStyle1);
                    author1TextFlow.setTextAlignment(TextAlignment.RIGHT);
                    // HBox for text flow
                    HBox author1HBox = new HBox(author1TextFlow);
                    author1HBox.setAlignment(Pos.BASELINE_RIGHT);
                    author1HBox.setMaxSize(1000, 1000);
                    // Add to VBox
                    vBox.getChildren().add(author1HBox);
                }
                //Second style of author
                if(part.getElementsByTag("strong").hasText()) {
                    Text authorStyle2 = new Text();
                    authorStyle2.setFill(Color.BLACK);
                    authorStyle2.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
                    // Text flow for paragraphs
                    TextFlow author2TextFlow = new TextFlow(authorStyle2);
                    author2TextFlow.setTextAlignment(TextAlignment.RIGHT);
                    // HBox for text flow
                    HBox author2HBox = new HBox(author2TextFlow);
                    author2HBox.setAlignment(Pos.BASELINE_RIGHT);
                    author2HBox.setMaxSize(1000, 1000);
                    // Add to VBox
                    vBox.getChildren().add(author2HBox);
                }
                //Third style of author
                if(part.select("p.author_mail").hasText()) {
                    Text authorStyle3 = new Text();
                    authorStyle3.setFill(Color.BLACK);
                    authorStyle3.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
                    // Text flow for paragraphs
                    TextFlow author3TextFlow = new TextFlow(authorStyle3);
                    author3TextFlow.setTextAlignment(TextAlignment.RIGHT);
                    // HBox for text flow
                    HBox author3HBox = new HBox(author3TextFlow);
                    author3HBox.setAlignment(Pos.BASELINE_RIGHT);
                    author3HBox.setMaxSize(1000, 1000);
                    // Add to VBox
                    vBox.getChildren().add(author3HBox);
                }
                vBox.getChildren().add(skipLine(10));
            }
        }catch (Selector.SelectorParseException ignored) {}
    }

    //Return empty text to simulate "enter"
    public static Text skipLine(double spacing) {
        Text emptyLine = new Text("");
        emptyLine.setFont(Font.font("Times New Roman", spacing));
        return emptyLine;
    }
    @Override
    public void start(Stage primaryStage) {

    }
}
