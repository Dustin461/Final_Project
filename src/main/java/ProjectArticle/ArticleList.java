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

    for (int i = 0; i<articleList.size(); i++) {
      if (articleList.get(i).getCategory() == category) {
        res.add(articleList.get(i));
      }
    }

    return res;
  }

  public ArrayList<Article> getNewsBySearch(String search) {
    ArrayList<Article> res = new ArrayList<Article>();

    for (int i = 0; i<articleList.size(); i++) {
      if (articleList.get(i).getTitle().contains(search)) {
        res.add(articleList.get(i));
      }
    }

    return res;
  }

  public void sortListByDate() {
    Collections.sort(articleList, (a1, a2) -> (int) (a2.getDate().getTime() - a1.getDate().getTime()));
  }
}
