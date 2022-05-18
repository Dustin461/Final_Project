package ProjectArticle;

import Main.Main;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.util.ArrayList;

public class ZingArticle extends Application {

    /** From here, we do the article management for ZingNews.vn **/

    //This function will scrap the data from the website
    public static ArrayList<Article> getZingArticleList(String url, String category) throws IOException {
        ArrayList<Article> zingArticleList = new ArrayList<>();

        //Set up Jsoup to scrap data from website
        Document doc = Jsoup.connect(url).get();
        try {
            Elements articles;
            if (category.equals("Newest")) { articles = doc.select("section#news-latest div.article-list article.article-item.type-text, section#news-latest div.article-list article.article-item.type-picture, section#news-latest div.article-list article.article-item.type-hasvideo"); }
            else { articles = doc.select("article"); }
            //Find the thumbnail image of the article
            Elements thumbnail = articles.select("p.article-thumbnail img");
            //Find the title and the source of the article
            Elements titleAndSource = articles.select("p.article-title a");
            //Find the description of the article
            Elements descriptionOfArticle = articles.select("p.article-summary");
            //Find the date and time of the article
            Elements dateOfArticle = articles.select("p.article-meta");
            //Find the original category of the article
            Elements pageCategoryOfArticle = articles.select("p.article-meta span.category-parent");

            int amountArticle = Math.min(articles.size(), 50);

            for (int i = 0; i < amountArticle; i++) {
                zingArticleList.add(new Article());
                //Set title of the article
                zingArticleList.get(i).setTitle(titleAndSource.get(i).text());
                //Set source of the article
                zingArticleList.get(i).setSource("ZINGNEWS.VN");
                //Set category of the article
                zingArticleList.get(i).setCategory(category);
                //Set original category of the article
                zingArticleList.get(i).setPageCategory(pageCategoryOfArticle.get(i).text());
                //Set date of the article
                String date = Helper.timeToUnixString3(dateOfArticle.get(i).select("span.date").text() + " " +
                        dateOfArticle.get(i).select("span.time").text());
                zingArticleList.get(i).setDate(date);
                //Set time duration of the article
                zingArticleList.get(i).setTimeDuration(Helper.timeDiff(date));
                //Set thumbnail image of the article
                if (thumbnail.get(i).hasAttr("data-src"))
                    zingArticleList.get(i).setThumbnail(thumbnail.get(i).attr("abs:data-src"));
                else {
                    if (thumbnail.get(i).hasAttr("src"))
                        zingArticleList.get(i).setThumbnail(thumbnail.get(i).attr("abs:src"));
                }
                //Set description of the article
                zingArticleList.get(i).setDescription(descriptionOfArticle.get(i).text());
            }
        } catch (Selector.SelectorParseException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            zingArticleList.remove(zingArticleList.size() - 1);
        }
        return zingArticleList;
    }

    //This function will get the list of article matching with the searched keyword
    public static ArrayList<Article> getListOfSearchZingArticle(String keyword, String category) throws IOException {
        ArrayList<Article> listOfSearchArticle = new ArrayList<>();
        //Convert the keyword to an url with format: https://zingnews.vn/key-word-tim-kiem.html?content_type=0
        String convertedLink = "https://zingnews.vn/" + keyword.trim().replaceAll("\\s", "-").toLowerCase() + "-tim-kiem.html?content_type=0";

        Document doc = Jsoup.connect(convertedLink).get();
        try {
            Elements articles = doc.select("div.article-list div.article-item");
            for (int i = 0; i <= articles.size(); i++) {
                listOfSearchArticle.add(new Article());
                //Set title of the article
                listOfSearchArticle.get(i).setTitle(articles.select("p.article-title").text());
                //Set source of the article
                listOfSearchArticle.get(i).setSource("ZINGNEWS.VN");
                //Set category of the article
                listOfSearchArticle.get(i).setCategory(category);
                //Set date and time of the article
                String date = Helper.timeToUnixString3(articles.get(i).select("p.article-meta span.date").text() + " " +
                        articles.get(i).select("p.article-meta span.time").text());
                listOfSearchArticle.get(i).setDate(date);
                //Set time duration of the article
                listOfSearchArticle.get(i).setTimeDuration(Helper.timeDiff(date));
                //Set thumbnail image of the article
                listOfSearchArticle.get(i).setThumbnail(articles.get(i).select("p.article-thumbnail img").attr("abs:data-src"));
                //Set description of the article
                listOfSearchArticle.get(i).setDescription(articles.get(i).select("p.article-summary").text());
            }

        } catch (Selector.SelectorParseException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            listOfSearchArticle.remove(listOfSearchArticle.size()-1);
        }

        return listOfSearchArticle;
    }

    public static void displayZingArticle(Article article, VBox vbox) throws IOException {
        vbox.getChildren().clear();
        String url = article.getLinkToArticle();
        Document doc = Jsoup.connect(url).get();
        try {
            Elements articles = doc.select("article[article-id");
            Elements date = articles.select("ul.the-article-meta li.the-article-publish");
            Elements author = articles.select("div.the-article-credit p.author");
            Elements pageCategory = articles.select("p.the-article-category a");
            //Elements video = articles.select("p.video-container.formatted.player-inited");
            Elements body = articles.select("div.the-article-body > p, td.pic img[src], td.pCaption.caption, figure.video.cms-video.player-inited");

            //Set date
            article.setDate(date.text());
            //Set author
            article.setAuthor(author.text());
            //Set original category
            article.setPageCategory(pageCategory.text());

            //Display logo
            Image logo = new Image("image/zing_logo_big.png", 200, 200, true, true, true);
            ImageView imageViewLogo = new ImageView();
            imageViewLogo.setImage(logo);
            imageViewLogo.setPreserveRatio(true);
            imageViewLogo.setCache(true);
            imageViewLogo.setSmooth(true);
            imageViewLogo.setFitHeight(60);
            vbox.getChildren().add(imageViewLogo);

            //Display category
            Text textCategory = new Text(article.getCategory());
            textCategory.setStyle("-fx-font-size:46;-fx-text-alignment:left;-fx-fill: black");
            TextFlow textFlow = new TextFlow(textCategory);
            vbox.getChildren().add(textFlow);

            //Display original category
            Text textOriginalCategory = new Text(article.getPageCategory());
            textOriginalCategory.setStyle("-fx-font-size:18;-fx-text-alignment:right;-fx-fill: black");
            TextFlow textFlow1 = new TextFlow(textOriginalCategory);
            textFlow1.setStyle("-fx-text-alignment: left");
            vbox.getChildren().add(textFlow1);

            //Display date
            Text textDate = new Text(article.getDate());
            textDate.setStyle("-fx-font-size:18;-fx-text-alignment:right;-fx-fill: black");
            TextFlow textFlow2 = new TextFlow(textDate);
            textFlow1.setStyle("-fx-text-alignment: left");
            vbox.getChildren().add(textFlow2);

            //Display title
            Text textTitle = new Text(article.getTitle());
            textTitle.setStyle("-fx-font-size:36;-fx-font-weight:bold; -fx-text-alignment:justify;-fx-fill: black;");
            TextFlow textFlow3 = new TextFlow(textTitle);
            textFlow1.setStyle("-fx-text-alignment: justify");
            vbox.getChildren().add(textFlow3);

            //Display description
            Text textDescription = new Text(article.getDescription());
            textDescription.setStyle("-fx-font-size: 18;-fx-font-weight: bold;-fx-text-alignment: justify;-fx-fill: black");
            TextFlow textFlow4 = new TextFlow(textDescription);
            textDescription.setStyle("-fx-text-alignment: justify");
            HBox hBoxOfDescription = new HBox();
            hBoxOfDescription.setStyle("-fx-padding: 15");
            hBoxOfDescription.getChildren().add(textFlow4);
            vbox.getChildren().add(hBoxOfDescription);

            //Display the body
            for (Element i : body) {
                TextFlow textFlow5 = new TextFlow();
                vbox.getChildren().add(textFlow5);

                //Display image
                if (!(i.attr("data-src").isEmpty() || i.attr("src").isEmpty())) {
                    ImageView imageView = new ImageView();
                    imageView.setCache(true);
                    imageView.setSmooth(true);
                    imageView.setPreserveRatio(true);
                    if (i.attr("data-src").isEmpty()) {
                        imageView.setImage(new Image(i.attr("abs:src"), 800, 0, true, true, true));
                    } else {
                        imageView.setImage(new Image(i.attr("abs:data-src"), 800, 0, true, true, true));
                    }
                    //Set fit width for imageview
                    if (Main.stage.getWidth() < 900) {
                        imageView.setFitWidth(Main.stage.getWidth() - 140);
                    }
                    if (Main.stage.getWidth() >= 900) {
                        imageView.setFitWidth(800);
                    }
                    vbox.getChildren().add(imageView);
                }
                //Display image caption
                if (i.hasClass("pCaption") && i.hasText()) {
                    if (!i.ownText().isEmpty()) {
                        Text textImageCaption = new Text(i.ownText());
                        textImageCaption.setStyle("-fx-font-size: 14;-fx-text-alignment: justify;-fx-fill: grey");
                        textFlow5.getChildren().add(textImageCaption);
                        textFlow5.setStyle("-fx-text-alignment: center");
                        continue;
                    }
                    Text textImageCaption1 = new Text(i.select("p").text());
                    textImageCaption1.setStyle("-fx-font-size: 14;-fx-text-alignment: justify;-fx-fill: grey");
                    textFlow5.getChildren().add(textImageCaption1);
                    textFlow5.setStyle("-fx-text-alignment: center");
                }

                // Display video
                if (i.hasClass("video")) {
                    Media media = new Media(i.select("div").attr("video-container"));
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    MediaView mediaView = new MediaView(mediaPlayer);
                    mediaView.setPreserveRatio(true);
                    mediaView.setCache(true);
                    mediaView.setSmooth(true);
                    //Set fit width for video view
                    if (Main.stage.getWidth() < 900) {
                        mediaView.setFitWidth(Main.stage.getWidth() - 140);
                    }
                    if (Main.stage.getWidth() >= 900) {
                        mediaView.setFitWidth(800);
                    }
                    vbox.getChildren().add(mediaView);
                }
                // Display video caption
                Text textVideoCaption = new Text(i.text());
                textVideoCaption.setStyle("-fx-font-size: 14;-fx-text-alignment: justify;-fx-fill: grey");
                TextFlow textFlow6 = new TextFlow();
                textFlow6.getChildren().add(textVideoCaption);
                textFlow6.setStyle("-fx-text-alignment: center");
                vbox.getChildren().add(textFlow6);

                // Display text/paragraph
                if(i.select("p").hasText()) {
                    Text textParagraph = new Text(i.text());
                    textParagraph.setStyle("-fx-font-size: 18;-fx-text-alignment: justify;-fx-fill: black");
                    textFlow5.getChildren().add(textParagraph);
                    textFlow5.setStyle("-fx-text-alignment: justify");
                }
            }

            //Display author
            TextFlow textFlow7 = new TextFlow();
            Text textAuthor = new Text(article.getAuthor());
            textAuthor.setStyle("-fx-font-size: 24;-fx-text-alignment: right;-fx-font-weight: bold");
            textFlow7.setStyle("-fx-text-alignment: right");
            vbox.getChildren().add(textFlow7);

        } catch (Selector.SelectorParseException ignored) {
        }
    }

    @Override
    public void start(Stage primaryStage) {
    }
}
