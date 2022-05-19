/** This file is used to test scrapping data
 * Ignore this file please
 */
package ProjectArticle;




import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;
import ProjectController.HomeSceneController;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



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

    public static ArrayList<Article> vnexpressNewestList = new ArrayList<>();
    public static ArrayList<Article> vnexpressCovidList = new ArrayList<>();
    public static ArrayList<Article> vnexpressPoliticsList = new ArrayList<>();
    public static ArrayList<Article> vnexpressBusinessList = new ArrayList<>();
    public static ArrayList<Article> vnexpressTechnologyList = new ArrayList<>();
    public static ArrayList<Article> vnexpressHealthList = new ArrayList<>();
    public static ArrayList<Article> vnexpressSportsList = new ArrayList<>();
    public static ArrayList<Article> vnexpressEntertainmentList = new ArrayList<>();
    public static ArrayList<Article> vnexpressWorldList = new ArrayList<>();
    public static ArrayList<Article> vnexpressOthersList = new ArrayList<>();
    public static ArrayList<Article> vnexpressSearchList = new ArrayList<>();

    // From Zing News
    public static ArrayList<Article> zingNewestList = new ArrayList<>();
    public static ArrayList<Article> zingCovidList = new ArrayList<>();
    public static ArrayList<Article> zingPoliticsList = new ArrayList<>();
    public static ArrayList<Article> zingBusinessList = new ArrayList<>();
    public static ArrayList<Article> zingTechnologyList = new ArrayList<>();
    public static ArrayList<Article> zingHealthList = new ArrayList<>();
    public static ArrayList<Article> zingSportsList = new ArrayList<>();
    public static ArrayList<Article> zingEntertainmentList = new ArrayList<>();
    public static ArrayList<Article> zingWorldList = new ArrayList<>();
    public static ArrayList<Article> zingOthersList = new ArrayList<>();
    public static ArrayList<Article> zingSearchList = new ArrayList<>();

    // From Tuoi tre
    public static ArrayList<Article> tuoiTreNewestList = new ArrayList<>();
    public static ArrayList<Article> tuoiTreCovidList = new ArrayList<>();
    public static ArrayList<Article> tuoiTrePoliticsList = new ArrayList<>();
    public static ArrayList<Article> tuoiTreBusinessList = new ArrayList<>();
    public static ArrayList<Article> tuoiTreTechnologyList = new ArrayList<>();
    public static ArrayList<Article> tuoiTreHealthList = new ArrayList<>();
    public static ArrayList<Article> tuoiTreSportsList = new ArrayList<>();
    public static ArrayList<Article> tuoiTreEntertainmentList = new ArrayList<>();
    public static ArrayList<Article> tuoiTreWorldList = new ArrayList<>();
    public static ArrayList<Article> tuoiTreOthersList = new ArrayList<>();
    public static ArrayList<Article> tuoiTreSearchList = new ArrayList<>();


    // From Thanh Nien
    public static ArrayList<Article> thanhNienNewestList = new ArrayList<>();
    public static ArrayList<Article> thanhNienCovidList = new ArrayList<>();
    public static ArrayList<Article> thanhNienPoliticsList = new ArrayList<>();
    public static ArrayList<Article> thanhNienBusinessList = new ArrayList<>();
    public static ArrayList<Article> thanhNienTechnologyList = new ArrayList<>();
    public static ArrayList<Article> thanhNienHealthList = new ArrayList<>();
    public static ArrayList<Article> thanhNienSportsList = new ArrayList<>();
    public static ArrayList<Article> thanhNienEntertainmentList = new ArrayList<>();
    public static ArrayList<Article> thanhNienWorldList = new ArrayList<>();
    public static ArrayList<Article> thanhNienOthersList = new ArrayList<>();
    public static ArrayList<Article> thanhNienSearchList = new ArrayList<>();

    // From Nhan Dan
    public static ArrayList<Article> nhanDanNewestList = new ArrayList<>();
    public static ArrayList<Article> nhanDanCovidList = new ArrayList<>();
    public static ArrayList<Article> nhanDanPoliticsList = new ArrayList<>();
    public static ArrayList<Article> nhanDanBusinessList = new ArrayList<>();
    public static ArrayList<Article> nhanDanTechnologyList = new ArrayList<>();
    public static ArrayList<Article> nhanDanHealthList = new ArrayList<>();
    public static ArrayList<Article> nhanDanSportsList = new ArrayList<>();
    public static ArrayList<Article> nhanDanEntertainmentList = new ArrayList<>();
    public static ArrayList<Article> nhanDanWorldList = new ArrayList<>();
    public static ArrayList<Article> nhanDanOthersList = new ArrayList<>();
    public static ArrayList<Article> nhanDanSearchList = new ArrayList<>();

    // Categories
    public static ArrayList<Article> newsList = new ArrayList<>();
    public static ArrayList<Article> covidList = new ArrayList<>();
    public static ArrayList<Article> politicsList = new ArrayList<>();
    public static ArrayList<Article> businessList = new ArrayList<>();
    public static ArrayList<Article> technologyList = new ArrayList<>();
    public static ArrayList<Article> healthList = new ArrayList<>();
    public static ArrayList<Article> sportsList = new ArrayList<>();
    public static ArrayList<Article> entertainmentList = new ArrayList<>();
    public static ArrayList<Article> worldList = new ArrayList<>();
    public static ArrayList<Article> othersList = new ArrayList<>();
    public static ArrayList<Article> searchList = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        /*
        //ArrayList<Article> newList = getVnExpressArticleNewest("https://vnexpress.net/rss/tin-moi-nhat.rss", "Newest");
        ArrayList<Article> newList = getVnExpressArticleList("https://vnexpress.net/thoi-su/chinh-tri", "Politics");

        printArticles(newList);
         */

        //HomeSceneController.progressBar.setProgress(0.0);


        // ZingNews Politics
        // Enter code here

        vnexpressPoliticsList.clear();
        vnexpressPoliticsList = VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/thoi-su/chinh-tri","Politics");
        //vnexpressPoliticsList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/thoi-su/chinh-tri-p2", "Politics"));


        // Tuoi Tre Politics

        tuoiTrePoliticsList.clear();
        tuoiTrePoliticsList = TuoiTreArticle.getListOfSearchTTArticle("chính trị", "Politics");



        // Thanh Nien Politics

        thanhNienPoliticsList.clear();
        thanhNienPoliticsList = ThanhNienArticle.getListOfSearchTNArticle("chính trị", "Politics");


        /*
        // Nhan Dan Politics
        es.execute(() -> {
            nhanDanPoliticsList.clear();
            try {
                nhanDanPoliticsList = NhanDanArticle.getListOfElementsInNhanDan("https://nhandan.vn", "Politics");
                //HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Enter code here
         */


        politicsList.clear();
        politicsList.addAll(getSortedArticlesList(vnexpressPoliticsList, zingPoliticsList, tuoiTrePoliticsList, thanhNienPoliticsList));

        int k = 1;
        for (Article i : politicsList) {
            System.out.println(k + ":");
            System.out.println("Source: " + deAccent(i.getSource()));
            System.out.println("Title: " + deAccent(i.getTitle()));
            System.out.println("Thumbnail: " + deAccent(i.getThumbnail()));
            System.out.println("Category: " + deAccent(i.getCategory()));
            System.out.println("Date: " + i.getDate());
            System.out.println("Age: " + i.getTimeDuration());
            System.out.println("Description: " + i.getDescription());
            System.out.println();
            k++;
        }


    }

    public static ArrayList<Article> getSortedArticlesList(ArrayList<Article> list1, ArrayList<Article> list2, ArrayList<Article> list3, ArrayList<Article> list4) {
        ArrayList<Article> sortedArticles = new ArrayList<>();

        sortedArticles.addAll(list1);
        sortedArticles.addAll(list2);
        sortedArticles.addAll(list3);
        sortedArticles.addAll(list4);
        //sortedArticles.addAll(list5);

        sortedArticles.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        return sortedArticles;
    }




}
