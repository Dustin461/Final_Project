package ProjectArticle;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.ArrayList;

public class VnExpressArticle extends Application {
    //Get the list of the newest article with RSS
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
            }
        } catch (Selector.SelectorParseException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            VnExpressNewestList.remove(VnExpressNewestList.size() - 1);
        }
        return VnExpressNewestList;
    }

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
            }

        }catch (Selector.SelectorParseException e) {
            return null;
        }catch (IndexOutOfBoundsException e) {
            VnExpressList.remove(VnExpressList.size() - 1);
        }
        return VnExpressList;
    }

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

    @Override
    public void start(Stage primaryStage) {

    }
}
