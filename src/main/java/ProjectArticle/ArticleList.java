package ProjectArticle;

import java.util.ArrayList;
import ProjectController.HomeSceneController;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ArticleList {
  // From VNExpress
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

  public ArticleList() {}

  public static void getNewestList() throws IOException {
    HomeSceneController.newestProgressBar.setProgress(0.0);
    HomeSceneController.progressBar.setVisible(false);

    ExecutorService es = Executors.newCachedThreadPool();
    // ZingNews Newest
    es.execute(() -> {
      zingNewestList.clear();
      try {
        zingNewestList = ZingArticle.getZingArticleList("https://zingnews.vn/", "News");
        getCovidList();
        getPoliticsList();
        getBusinessList();
        getTechnologyList();
        getHealthList();
        getSportsList();
        getEntertainmentList();
        getWorldList();
        HomeSceneController.newestProgressBar.setProgress(HomeSceneController.newestProgressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress Newest
    // Enter code here
    es.execute(() -> {
      vnexpressNewestList.clear();
      try {
        vnexpressNewestList = VnExpressArticle.getVnExpressArticleNewest("https://vnexpress.net/rss/tin-moi-nhat.rss", "Newest");
        getCovidList();
        getPoliticsList();
        getBusinessList();
        getTechnologyList();
        getHealthList();
        getSportsList();
        getEntertainmentList();
        getWorldList();
        //WelcomeSceneController.progressBar.setProgress(WelcomeSceneController.progressBar.getProgress() + 0.2);
        HomeSceneController.newestProgressBar.setProgress(HomeSceneController.newestProgressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Tuoi Tre Newest
    es.execute(() -> {
      tuoiTreNewestList.clear();
      try {
        tuoiTreNewestList = TuoiTreArticle.getListOfElementsInTT("https://tuoitre.vn", "Newest");
        getCovidList();
        getPoliticsList();
        getBusinessList();
        getTechnologyList();
        getHealthList();
        getSportsList();
        getEntertainmentList();
        getWorldList();
        //WelcomeSceneController.progressBar.setProgress(WelcomeSceneController.progressBar.getProgress() + 0.2);
        HomeSceneController.newestProgressBar.setProgress(HomeSceneController.newestProgressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Thanh Nien Newest
    es.execute(() -> {
      thanhNienNewestList.clear();
      try {
        thanhNienNewestList = ThanhNienArticle.getListOfElementsInTN("https://thanhnien.vn", "Newest");
        getCovidList(); getPoliticsList();
        //WelcomeSceneController.progressBar.setProgress(WelcomeSceneController.progressBar.getProgress() + 0.2);
        HomeSceneController.newestProgressBar.setProgress(HomeSceneController.newestProgressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan Newest
    // Enter code here

    es.shutdown();


    newsList.clear();
    newsList.addAll(vnexpressNewestList);
    newsList.addAll(zingNewestList);
    newsList.addAll(tuoiTreNewestList);
    newsList.addAll(thanhNienNewestList);
    newsList.addAll(nhanDanNewestList);
    newsList.addAll(covidList);
    newsList.addAll(politicsList);
    newsList.addAll(businessList);
    newsList.addAll(technologyList);
    newsList.addAll(healthList);
    newsList.addAll(sportsList);
    newsList.addAll(worldList);
    newsList.addAll(entertainmentList);
    newsList.addAll(othersList);
    sortList(newsList);
    removeDuplicateArticle(newsList);

    HomeSceneController.progressBar.setVisible(true);
  }


  public static void getCovidList() {
    HomeSceneController.progressBar.setProgress(0.0);

    ExecutorService es = Executors.newCachedThreadPool();

    // ZingNews Covid
    // Enter code here
    es.execute(() -> {
      zingCovidList.clear();
      try {
        //ZingNews doesn't have Covid Category
        zingCovidList = ZingArticle.getListOfSearchZingArticle("covid", "Covid");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress Covid
    // Enter code here
    es.execute(() -> {
      vnexpressCovidList.clear();
      try {
        vnexpressCovidList = VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/covid-19/tin-tuc", "Covid");
        vnexpressCovidList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/covid-19/tin-tuc-p2", "Covid"));
        vnexpressCovidList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/covid-19/tin-tuc-p3", "Covid"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Tuoi Tre Covid
    es.execute(() -> {
      tuoiTreCovidList.clear();
      try {
        tuoiTreCovidList = TuoiTreArticle.getListOfSearchTTArticle("covid", "Covid");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Thanh Nien Covid
    es.execute(() -> {
      thanhNienCovidList.clear();
      try {
        thanhNienCovidList = ThanhNienArticle.getListOfSearchTNArticle("covid", "Covid");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan Covid
    // Enter code here

    es.shutdown();

    covidList.clear();
    covidList.addAll(getSortedArticlesList(vnexpressCovidList, zingCovidList, tuoiTreCovidList, thanhNienCovidList, nhanDanCovidList));
  }

  public static void getPoliticsList() {
    HomeSceneController.progressBar.setProgress(0.0);

    ExecutorService es = Executors.newCachedThreadPool();
    // ZingNews Politics
    // Enter code here
    es.execute(() -> {
      zingPoliticsList.clear();
      try {
        zingPoliticsList = ZingArticle.getZingArticleList("https://zingnews.vn/chinh-tri.html/?page=1", "Politics");
        zingPoliticsList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/chinh-tri.html/?page=2", "Politics"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress Politics
    // Enter code here
    es.execute(() -> {
      vnexpressPoliticsList.clear();
      try {
        vnexpressPoliticsList = VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/thoi-su/chinh-tri", "Politics");
        vnexpressPoliticsList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/thoi-su/chinh-tri-p2", "Politics"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Tuoi Tre Politics
    es.execute(() -> {
      tuoiTrePoliticsList.clear();
      try {
        tuoiTrePoliticsList = TuoiTreArticle.getListOfSearchTTArticle("chính trị", "Politics");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Thanh Nien Politics
    es.execute(() -> {
      thanhNienPoliticsList.clear();
      try {
        thanhNienPoliticsList = ThanhNienArticle.getListOfSearchTNArticle("chính trị", "Politics");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan Politics
    // Enter code here

    es.shutdown();

    politicsList.clear();
    politicsList.addAll(getSortedArticlesList(vnexpressPoliticsList, zingPoliticsList, tuoiTrePoliticsList, thanhNienPoliticsList, nhanDanPoliticsList));
  }

  public static void getBusinessList() throws IOException {
    HomeSceneController.progressBar.setProgress(0.0);

    ExecutorService es = Executors.newCachedThreadPool();

    // ZingNews Business
    // Enter code here
    es.execute(() -> {
      zingBusinessList.clear();
      try {
        zingBusinessList = ZingArticle.getZingArticleList("https://zingnews.vn/kinh-doanh-tai-chinh.html", "Business");
        zingBusinessList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/kinh-doanh-tai-chinh.html/?page=2", "Business"));
        zingBusinessList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/kinh-doanh-tai-chinh.html/?page=3", "Business"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress Business
    // Enter code here
    es.execute(() -> {
      vnexpressBusinessList.clear();
      try {
        vnexpressBusinessList = VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/kinh-doanh", "Business");
        vnexpressBusinessList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/kinh-doanh-p2", "Business"));
        vnexpressBusinessList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/kinh-doanh-p3", "Business"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Tuoi Tre Business
    es.execute(() -> {
      tuoiTreBusinessList.clear();
      try {
        tuoiTreBusinessList = TuoiTreArticle.getListOfElementsInTT("https://tuoitre.vn/kinh-doanh.htm", "Business");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Thanh Nien Business
    es.execute(() -> {
      thanhNienBusinessList.clear();
      try {
        thanhNienBusinessList = ThanhNienArticle.getListOfSearchTNArticle("kinh doanh", "Business");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan Business
    // Enter code here

    es.shutdown();

    businessList.clear();
    businessList.addAll(getSortedArticlesList(vnexpressBusinessList, zingBusinessList, tuoiTreBusinessList, thanhNienBusinessList, nhanDanBusinessList));
  }

  public static void getTechnologyList() throws IOException {
    HomeSceneController.progressBar.setProgress(0.0);

    ExecutorService es = Executors.newCachedThreadPool();
    // ZingNews Technology
    // Enter code here

    es.execute(() -> {
      zingTechnologyList.clear();
      try {
//                zingTechnologyList = ArticlesManager.getZingWebList("https://zingnews.vn/cong-nghe.html", "Technology");
        zingTechnologyList = ZingArticle.getZingArticleList("https://zingnews.vn/cong-nghe.html/?page=1", "Technology");
        zingTechnologyList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/cong-nghe.html/?page=2", "Technology"));
        zingTechnologyList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/cong-nghe.html/?page=3", "Technology"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress Technology
    // Enter code here
    es.execute(() -> {
      vnexpressTechnologyList.clear();
      try {
        vnexpressTechnologyList = VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/so-hoa/cong-nghe", "Technology");
        vnexpressTechnologyList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/so-hoa/cong-nghe-p2", "Technology"));
        vnexpressTechnologyList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/so-hoa/cong-nghe-p3", "Technology"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Tuoi Tre Technology
    es.execute(() -> {
      tuoiTreTechnologyList.clear();
      try {
        tuoiTreTechnologyList = TuoiTreArticle.getListOfElementsInTT("https://congnghe.tuoitre.vn", "Technology");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Thanh Nien Technology
    es.execute(() -> {
      thanhNienTechnologyList.clear();
      try {
        thanhNienTechnologyList = ThanhNienArticle.getListOfSearchTNArticle("công nghệ", "Technology");
        thanhNienTechnologyList.addAll(ThanhNienArticle.getListOfSearchTNArticle("blockchain", "Technology"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan Technology
    // Enter code here

    es.shutdown();

    technologyList.clear();
    technologyList.addAll(getSortedArticlesList(vnexpressTechnologyList, zingTechnologyList, tuoiTreTechnologyList, thanhNienTechnologyList, nhanDanTechnologyList));
  }

  public static void getHealthList() throws IOException {
    HomeSceneController.progressBar.setProgress(0.0);

    ExecutorService es = Executors.newCachedThreadPool();

    // ZingNews Health
    // Enter code here
    es.execute(() -> {
      zingHealthList.clear();
      try {
        zingHealthList = ZingArticle.getZingArticleList("https://zingnews.vn/suc-khoe.html", "Health");
        zingHealthList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/suc-khoe.html/?page=2", "Health"));
        zingHealthList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/suc-khoe.html/?page=3", "Health"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress Health
    // Enter code here
    es.execute(() -> {
      vnexpressHealthList.clear();
      try {
        vnexpressHealthList = VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/suc-khoe", "Health");
        vnexpressHealthList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/suc-khoe-p2", "Health"));
        vnexpressHealthList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/suc-khoe-p3", "Health"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Tuoi Tre Health
    es.execute(() -> {
      tuoiTreHealthList.clear();
      try {
        tuoiTreHealthList = TuoiTreArticle.getListOfElementsInTT("https://tuoitre.vn/suc-khoe.htm", "Health");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.2);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Thanh Nien Health
    es.execute(() -> {
      thanhNienHealthList.clear();
      try {
        thanhNienHealthList = ThanhNienArticle.getListOfElementsInTN("https://thanhnien.vn/suc-khoe/", "Health");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.2);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan Health
    // Enter code here

    es.shutdown();

    healthList.clear();
    healthList.addAll(getSortedArticlesList(vnexpressHealthList, zingHealthList, tuoiTreHealthList, thanhNienHealthList, nhanDanHealthList));
  }

  public static void getSportsList() throws IOException {
    HomeSceneController.progressBar.setProgress(0.0);

    ExecutorService es = Executors.newCachedThreadPool();

    // ZingNews Sports
    // Enter code here
    es.execute(() -> {
      zingSportsList.clear();
      try {
        zingSportsList = ZingArticle.getZingArticleList("https://zingnews.vn/the-thao.html", "Sport");
        zingSportsList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/the-thao.html/?page=2", "Sport"));
        zingSportsList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/the-thao.html/?page=3", "Sport"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress Sports
    // Enter code here
    es.execute(() -> {
      vnexpressSportsList.clear();
      try {
        vnexpressSportsList = VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/the-thao", "Sport");
        vnexpressSportsList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/the-thao-p2", "Sport"));
        vnexpressSportsList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/the-thao-p3", "Sport"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Tuoi Tre Sports
    es.execute(() -> {
      tuoiTreSportsList.clear();
      try {
        tuoiTreSportsList = TuoiTreArticle.getListOfElementsInTT("https://tuoitre.vn/the-thao.htm", "Sport");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Thanh Nien Sports
    es.execute(() -> {
      thanhNienSportsList.clear();
      try {
        thanhNienSportsList = ThanhNienArticle.getListOfElementsInTN("https://thanhnien.vn/the-thao/", "Sport");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan Sports
    // Enter code here

    es.shutdown();

    sportsList.clear();
    sportsList.addAll(getSortedArticlesList(vnexpressSportsList, zingSportsList, tuoiTreSportsList, thanhNienSportsList, nhanDanSportsList));
  }

  public static void getEntertainmentList() throws IOException {
    HomeSceneController.progressBar.setProgress(0.0);

    ExecutorService es = Executors.newCachedThreadPool();

    // ZingNews Entertainment
    // Enter code here
    es.execute(() -> {
      zingEntertainmentList.clear();
      try {
        zingEntertainmentList = ZingArticle.getZingArticleList("https://zingnews.vn/giai-tri.html", "Entertainment");
        zingEntertainmentList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/giai-tri.html/?page=2", "Entertainment"));
        zingEntertainmentList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/giai-tri.html/?page=3", "Entertainment"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress Entertainment
    // Enter code here
    es.execute(() -> {
      vnexpressEntertainmentList.clear();
      try {
        vnexpressEntertainmentList = VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/giai-tri", "Entertainment");
        vnexpressEntertainmentList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/giai-tri-p2", "Entertainment"));
        vnexpressEntertainmentList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/giai-tri-p3", "Entertainment"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });


    // Tuoi Tre Entertainment
    es.execute(() -> {
      tuoiTreEntertainmentList.clear();
      try {
        tuoiTreEntertainmentList = TuoiTreArticle.getListOfElementsInTT("https://tuoitre.vn/giai-tri.htm", "Entertainment");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Thanh Nien Entertainment
    es.execute(() -> {
      thanhNienEntertainmentList.clear();
      try {
        thanhNienEntertainmentList = ThanhNienArticle.getListOfElementsInTN("https://thanhnien.vn/giai-tri/", "Entertainment");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan Entertainment
    // Enter code here

    es.shutdown();

    entertainmentList.clear();
    entertainmentList.addAll(getSortedArticlesList(vnexpressEntertainmentList, zingEntertainmentList, tuoiTreEntertainmentList, thanhNienEntertainmentList, nhanDanEntertainmentList));
  }

  public static void getWorldList() throws IOException {
    HomeSceneController.progressBar.setProgress(0.0);

    ExecutorService es = Executors.newCachedThreadPool();

    // ZingNews World
    // Enter code here
    es.execute(() -> {
      zingWorldList.clear();
      try {
        zingWorldList = ZingArticle.getZingArticleList("https://zingnews.vn/the-gioi.html", "World");
        zingWorldList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/the-gioi.html/?page=2", "World"));
        zingWorldList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/the-gioi.html/?page=3", "World"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress World
    // Enter code here
    es.execute(() -> {
      vnexpressWorldList.clear();
      try {
        vnexpressWorldList = VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/the-gioi", "World");
        vnexpressWorldList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/the-gioi-p2", "World"));
        vnexpressWorldList.addAll(VnExpressArticle.getVnExpressArticleList("https://vnexpress.net/the-gioi-p3", "World"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Tuoi Tre World
    es.execute(() -> {
      tuoiTreWorldList.clear();
      try {
        tuoiTreWorldList = TuoiTreArticle.getListOfElementsInTT("https://tuoitre.vn/the-gioi.htm", "World");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Thanh Nien World
    es.execute(() -> {
      thanhNienWorldList.clear();
      try {
        thanhNienWorldList = ThanhNienArticle.getListOfElementsInTN("https://thanhnien.vn/the-gioi/", "World");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan World
    // Enter code here

    es.shutdown();

    worldList.clear();
    worldList.addAll(getSortedArticlesList(vnexpressWorldList, zingWorldList, tuoiTreWorldList, thanhNienWorldList, nhanDanWorldList));
  }

  public static void getOthersList() throws IOException {
    HomeSceneController.progressBar.setProgress(0.0);
    ExecutorService es = Executors.newCachedThreadPool();

    // ZingNews Others
    // Enter code here
    es.execute(() -> {
      zingOthersList.clear();
      try {
        zingOthersList = ZingArticle.getZingArticleList("https://zingnews.vn/du-hoc.html", "Others");
        zingOthersList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/oto.html", "Others"));
        zingOthersList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/xu-huong.html", "Others"));
        zingOthersList.addAll(ZingArticle.getZingArticleList("https://zingnews.vn/am-thuc.html", "Others"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress Others
    // Enter code here
    es.execute(() -> {
      vnexpressOthersList.clear();
      try {
        vnexpressOthersList = VnExpressArticle.getVnExpressArticleNewest("https://vnexpress.net/rss/phap-luat.rss", "Others");
        vnexpressOthersList.addAll(VnExpressArticle.getVnExpressArticleNewest("https://vnexpress.net/rss/tam-su.rss", "Others"));
        vnexpressOthersList.addAll(VnExpressArticle.getVnExpressArticleNewest("https://vnexpress.net/rss/du-lich.rss", "Others"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Tuoi Tre Others
    es.execute(() -> {
      tuoiTreOthersList.clear();
      try {
        tuoiTreOthersList = TuoiTreArticle.getListOfElementsInTT("https://tuoitre.vn/khoa-hoc.htm", "Others");
        tuoiTreOthersList.addAll(TuoiTreArticle.getListOfElementsInTT("https://tuoitre.vn/phap-luat.htm", "Others"));
        tuoiTreOthersList.addAll(TuoiTreArticle.getListOfElementsInTT("https://tuoitre.vn/gia-that.htm", "Others"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Thanh Nien Others
    es.execute(() -> {
      thanhNienOthersList.clear();
      try {
        thanhNienOthersList = ThanhNienArticle.getListOfElementsInTN("https://thanhnien.vn/van-hoa/", "Others");
        thanhNienOthersList.addAll(ThanhNienArticle.getListOfElementsInTN("https://thanhnien.vn/doi-song/", "Others"));
        thanhNienOthersList.addAll(ThanhNienArticle.getListOfElementsInTN("https://thanhnien.vn/giao-duc/", "Others"));
        thanhNienOthersList.addAll(ThanhNienArticle.getListOfElementsInTN("https://thanhnien.vn/ban-doc/", "Others"));
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan Others
    // Enter code here

    es.shutdown();

    othersList.clear();
    othersList.addAll(getSortedArticlesList(vnexpressOthersList, zingOthersList, tuoiTreOthersList, thanhNienOthersList, nhanDanOthersList));
  }

  public static void getSearchList(String keyWord) throws IOException {
    HomeSceneController.progressBar.setProgress(0.0);

    ExecutorService es = Executors.newCachedThreadPool();

    // ZingNews Search
    // Enter code here
    es.execute(() -> {
      zingSearchList.clear();
      try {
        zingSearchList = ZingArticle.getListOfSearchZingArticle(keyWord, "Search");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // VNExpress Search
    // Enter code here
    es.execute(() -> {
      vnexpressSearchList.clear();
      try {
        vnexpressSearchList = VnExpressArticle.getListOfSearchVEArticle(keyWord, "Search");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    es.execute(() -> {
      tuoiTreSearchList.clear();
      try {
        tuoiTreSearchList = TuoiTreArticle.getListOfSearchTTArticle(keyWord, "Search");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    es.execute(() -> {
      thanhNienSearchList.clear();
      try {
        thanhNienSearchList = ThanhNienArticle.getListOfSearchTNArticle(keyWord, "Search");
        HomeSceneController.progressBar.setProgress(HomeSceneController.progressBar.getProgress() + 0.25);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Nhan Dan Search
    // Enter code here

    es.shutdown();

    try {
      searchList.clear();
      searchList.addAll(getSortedArticlesList(vnexpressSearchList, zingSearchList, tuoiTreSearchList, thanhNienSearchList, nhanDanSearchList));
    } catch (Exception e) {
      System.out.println("There is nothing to show try-catch (search list: zero element)");
    }
  }

  public static void sortList(ArrayList<Article> list) {
    list.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
  }

  public static void removeDuplicateArticle(ArrayList<Article> list) {
    HashSet<String> hashSet = new HashSet<>();
    ArrayList<Article> toRemove = new ArrayList<>();
    for (Article article : list) {
      if (hashSet.contains(article.getLinkToArticle())) {
        toRemove.add(article);
      } else {
        hashSet.add(article.getLinkToArticle());
      }
    }
    list.removeAll(toRemove);
  }

  public static ArrayList<Article> getSortedArticlesList(ArrayList<Article> list1, ArrayList<Article> list2, ArrayList<Article> list3, ArrayList<Article> list4, ArrayList<Article> list5) {
    ArrayList<Article> sortedArticles = new ArrayList<>();

    sortedArticles.addAll(list1);
    sortedArticles.addAll(list2);
    sortedArticles.addAll(list3);
    sortedArticles.addAll(list4);
    sortedArticles.addAll(list5);

    sortedArticles.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

    return sortedArticles;
  }


}
