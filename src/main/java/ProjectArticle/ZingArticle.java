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
        ArrayList<Article> listElementsOfZing = new ArrayList<>();

        //Set up Jsoup to scrap data from website
        Document doc = Jsoup.connect(url).get();
        try {
            Elements categoryFeatured = doc.select("section#category-featured div.article-list");
            //Find the thumbnail image of the article
            Elements thumbnailOfArticle = categoryFeatured.select("p.article-thumbnail img");
            //Find the title of the article
            Elements titleOfArticle = categoryFeatured.select("p.article-title");
            //Find the description of the article
            Elements descriptionOfArticle = categoryFeatured.select("p.article-summary");
            //Find the date and time of the article
            Elements dateOfArticle = categoryFeatured.select("p.article-meta");
            //Find the original category of the article
            Elements pageCategoryOfArticle = categoryFeatured.select("p.article-meta");


            for (int i = 0; i <= 50; i++) {
                listElementsOfZing.add(new Article());
                //Set title of the article
                listElementsOfZing.get(i).setTitle(titleOfArticle.get(i).select("p.article-title").text());
                //Set source of the article
                listElementsOfZing.get(i).setSource("ZINGNEWS.VN");
                //Set category of the article
                listElementsOfZing.get(i).setCategory(category);
                //Set original category of the article
                listElementsOfZing.get(i).setPageCategory(pageCategoryOfArticle.select("span.category").text());
                //Set date and time of the article
                listElementsOfZing.get(i).setDate(dateOfArticle.get(i).select("span.date").text() + " " + dateOfArticle.get(i).select("span.time").text());
                //Set time duration of the article
                listElementsOfZing.get(i).setTimeDuration(dateOfArticle.get(i).select("span.friendly-time").text());
                //Set thumbnail image of the article
                listElementsOfZing.get(i).setThumbnail(thumbnailOfArticle.get(i).attr("src"));
                //Set description of the article
                listElementsOfZing.get(i).setDescription(descriptionOfArticle.get(i).text());
            }

        } catch (Selector.SelectorParseException spe) {
            spe.printStackTrace();
            return listElementsOfZing;
        }


        return listElementsOfZing;
    }

    //This function will get the list of article matching with the searched keyword
    public static ArrayList<Article> getListOfSearchArticle(String keyword, String category) throws IOException {
        ArrayList<Article> listOfSearchArticle = new ArrayList<>();
        //Convert the keyword to an url with format: https://zingnews.vn/key-word-tim-kiem.html?content_type=0
        String convertedLink = "https://zingnews.vn/" + keyword.trim().replaceAll("\\s", "-").toLowerCase() + "-tim-kiem.html?content_type=0";

        Document doc = Jsoup.connect(convertedLink).get();
        try {
            Elements categoryFeatured = doc.select("div.article-list div.article-item");
            for (int i = 0; i <= categoryFeatured.size(); i++) {
                listOfSearchArticle.add(new Article());
                //Set title of the article
                listOfSearchArticle.get(i).setTitle(categoryFeatured.select("p.article-title").text());
                //Set source of the article
                listOfSearchArticle.get(i).setSource("ZINGNEWS.VN");
                //Set category of the article
                listOfSearchArticle.get(i).setCategory(category);
                //Set date and time of the article
                listOfSearchArticle.get(i).setDate(categoryFeatured.get(i).select("span.date").text() + " " +
                        categoryFeatured.get(i).select("span.time").text());
                //Set time duration of the article
                listOfSearchArticle.get(i).setTimeDuration(categoryFeatured.get(i).select("span.friendly-time").text());
                //Set thumbnail image of the article
                listOfSearchArticle.get(i).setThumbnail(categoryFeatured.get(i).select("p.article-thumbnail img").attr("src"));
                //Set description of the article
                listOfSearchArticle.get(i).setDescription(categoryFeatured.get(i).select("p.article-summary").text());
            }

        } catch (Selector.SelectorParseException spe) {
            spe.printStackTrace();
            return listOfSearchArticle;
        }

        return listOfSearchArticle;
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
