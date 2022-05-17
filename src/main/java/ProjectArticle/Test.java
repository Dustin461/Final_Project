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
                VnExpressList.get(i).setThumbnail(thumbnailOfArticle.get(i).getElementsByTag("img").attr("data-src"));

            }

        }catch (Selector.SelectorParseException e) {
            return null;
        }catch (IndexOutOfBoundsException e) {
            VnExpressList.remove(VnExpressList.size() - 1);
        }


        return VnExpressList;
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
        //ArrayList<Article> newList = getVnExpressArticleNewest("https://vnexpress.net/rss/tin-moi-nhat.rss", "Newest");
        ArrayList<Article> newList = getVnExpressArticleList("https://vnexpress.net/thoi-su/chinh-tri", "Politics");
        assert newList != null;
        printArticles(newList);

    }


}
