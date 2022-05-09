package ProjectArticle;

import java.util.ArrayList;
import java.util.Collections;



public class ArticleList {
  private ArrayList<Article> articleList;

  public ArticleList(ArrayList<Article> articleList) {
    this.articleList = articleList;
  }

  public ArticleList() {
    this.articleList = new ArrayList<Article>();
  }

  public void addArticle(Article article) {
    this.articleList.add(article);
  }

  public ArrayList<Article> getArticleList() {
    return this.articleList;
  }

  public ArrayList<Article> getNewsByCategory(String category) {
    ArrayList<Article> res = new ArrayList<Article>();

    for (Article article : articleList) {
      if (article.getCategory().equals(category)) {
        res.add(article);
      }
    }

    return res;
  }

  public ArrayList<Article> getNewsBySearch(String search) {
    ArrayList<Article> res = new ArrayList<Article>();

    for (Article article : articleList) {
      if (article.getTitle().contains(search)) {
        res.add(article);
      }
    }

    return res;
  }

  public void sortListByDate() {
    Collections.sort(articleList, (a1, a2) -> (int) (a2.getDate().getTime() - a1.getDate().getTime()));
  }
}
