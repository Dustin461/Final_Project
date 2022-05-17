package ProjectArticle;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NhanDanArticle {

    private static final HashMap<String, String> categoriesMap  = new HashMap<String, String>() {{
        put("Politics", "chinhtri");
        put("Business", "kinhte");
        put("Technology", "khoahoc-congnghe");
        put("Health", "y-te");
        put("Sports", "thethao");
        put("World", "thegioi");
    }};

    public static ArrayList<Article> getNhanDanArticleList(String url, String category) throws IOException{
        //url += categoriesMap.get(category);
        ArrayList<Article> nhanDanArticleList = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        //System.out.println(doc);

        try {
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

                nhanDanArticleList.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhanDanArticleList;
    }

    public static ArrayList<Article> getListOfSearchNDArticle(String keyword, String category) throws IOException{
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
                article.setCategory(category);

                String dateStr = date.get(i).text();
                String dateUnix = Helper.timeToUnixString3Reverse(dateStr);
                article.setDate(dateUnix);

                article.setTimeDuration(Helper.timeDiff(dateUnix));

                article.setThumbnail(thumbnail.get(i).attr("data-src"));

                nhanDanSearchArticleList.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nhanDanSearchArticleList;
    }

    public static void main(String[] args) throws IOException{
        System.out.println(getListOfSearchNDArticle("nhan", "Politics").toString());
        ArrayList<Article> newList = getNhanDanArticleList("https://nhandan.vn/chinhtri", "Newest");
        //assert newList != null;
        printArticles(newList);
    }

    public static void printArticles(ArrayList<Article> list) {
        int k = 1;
        for (Article i : list) {
            System.out.println(k + ":");
            System.out.println("Source: " + i.getSource());
            System.out.println("Title: " + i.getTitle());
            System.out.println("Thumbnail: " + i.getThumbnail());
            System.out.println("Category: " + i.getPageCategory());
            System.out.println("Age: " + i.getTimeDuration());
            System.out.println("Author: " + i.getAuthor());
            System.out.println();
            k++;
        }
    }

}
