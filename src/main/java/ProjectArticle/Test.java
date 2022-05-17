/** This file is used to test scrapping data
 * Ignore this file please
 */
package ProjectArticle;



import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;
import Main.Main;


public class Test {
    public static ArrayList<Article> getZingArticleList(String url, String category) throws IOException {
        final int MAX_ARTICLES = 50;
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
            //Elements articleEl = doc.select("article[article-id]");
            Elements authorOfArticle = doc.select("div.the-article-credit p.author");
            int size = Math.min(articles.size(), 50);
            for (int i = 0; i < 50; i++) {
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
    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static void printArticles(ArrayList<Article> list) {
        int k = 1;
        for (Article i : list) {
            System.out.println(k + ":");
            System.out.println("Source: " + deAccent(i.getSource()));
            System.out.println("Title: " + deAccent(i.getTitle()));
            System.out.println("Thumbnail: " + deAccent(i.getThumbnail()));
            System.out.println("Category: " + deAccent(i.getPageCategory()));
            System.out.println("Age: " + i.getTimeDuration());
            System.out.println("Author: " + i.getAuthor());
            System.out.println("Description: " + i.getDescription());
            System.out.println();
            k++;
        }

    }

    public static void main(String[] args) throws IOException {
        ArrayList<Article> newList = getZingArticleList("https://zingnews.vn/the-gioi.html", "Newest");
        assert newList != null;
        printArticles(newList);

    }

    public static void displayZingArticle(Article article, VBox vbox) throws IOException {
        //clear the vbox to display new article
        vbox.getChildren().clear();
        String url = article.getLinkToArticle();
        Document doc = Jsoup.connect(url).get();

        Elements articles = doc.select("article[article-id");
        Elements date = articles.select("ul.the-article-meta li.the-article-publish");
        Elements author = articles.select("div.the-article-credit p.author");
        Elements pageCategory = articles.select("p.the-article-category a");
        Elements video = articles.select("p.video-container.formatted.player-inited");
        Elements body = articles.select("div.the-article-body > p, td.pic img[src], td.pCaption.caption, figure.video.cms-video.player-inited");

        //Set date
        article.setDate(date.text());
        //Set author
        article.setAuthor(author.text());
        //Set original category
        article.setPageCategory(pageCategory.text());

        //Display image of ZingNews source
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
    }
}
