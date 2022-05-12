/** This file is used to test scrapping data
 * Ignore this file please
 */
package ProjectArticle;

import javafx.stage.Stage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;


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
                    //Set description of the article
                    zingArticleList.get(i).setDescription(descriptionOfArticle.get(i).text());
                }

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
            System.out.println();
            k++;
        }

    }

    public static void main(String[] args) throws IOException {
        ArrayList<Article> newList = getZingArticleList("https://zingnews.vn/the-gioi.html", "Newest");
        assert newList != null;
        printArticles(newList);

    }

}
