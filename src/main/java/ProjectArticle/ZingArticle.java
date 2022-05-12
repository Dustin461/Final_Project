package ProjectArticle;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.ArrayList;

public class ZingArticle extends Application {

    /** From here, we do the article management for ZingNews.vn **/

    //This function will scrap the data from the website
    public static ArrayList<Article> getListOfElementsInZing(String url, String category) throws IOException {
        final int MAX_ARTICLES = 50;
        ArrayList<Article> zingArticleList = new ArrayList<>();

        //Set up Jsoup to scrap data from website
        Document doc = Jsoup.connect(url).get();
        try {
            Elements articles;
            if (category.equals("Newest")) { articles = doc.select("section#section-featured, section#section-latest div.article-list.listing-layout.responsive.unique"); }
            else { articles = doc.select("section#news-latest div.article-list.listing-layout.responsive.infinite-load"); }
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


            for (int i = 0; i <= MAX_ARTICLES; i++) {
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
                    //Set description of the article
                    zingArticleList.get(i).setDescription(descriptionOfArticle.get(i).text());
                }
            }
        } catch (Selector.SelectorParseException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return zingArticleList;
    }

    //This function will get the list of article matching with the searched keyword
    public static ArrayList<Article> getListOfSearchArticle(String keyword, String category) throws IOException {
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
            e.printStackTrace();
        }

        return listOfSearchArticle;
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
