package ProjectArticle;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;

public class Article {
  private String date;
  private String title;
  private String timeDuration;
  private String pageCategory;
  private String linkToArticle;
  private String category;
  private String description;
  private String source;
  private String author;
  private String thumbnail;

  public Article(String date, String title, String timeDuration, String pageCategory, String linkToArticle, String category, String description, String source, String author, String thumbnail) {
    setDate(date);
    setTitle(title);
    setTimeDuration(timeDuration);
    setPageCategory(pageCategory);
    setLinkToArticle(linkToArticle);
    setCategory(category);
    setDescription(description);
    setSource(source);
    setAuthor(author);
    setThumbnail(thumbnail);
  }

  public Article(){
    setDate("");
    setTitle("");
    setTimeDuration("");
    setPageCategory("");
    setLinkToArticle("");
    setCategory("");
    setDescription("");
    setSource("");
    setAuthor("");
    setThumbnail("");
  }

  //Setter
  public void setDate(String date) {
    this.date = date;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public void setTimeDuration(String timeDuration) {
    this.timeDuration = timeDuration;
  }
  public void setPageCategory(String pageCategory) {
    this.pageCategory = pageCategory;
  }
  public void setLinkToArticle(String link) {
    this.linkToArticle = link;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public void setSource(String source) {
    this.source = source;
  }
  public void setAuthor(String author) {
    this.author = author;
  }
  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  //Getter
  public String getDate() {
    return date;
  }
  public String getTimeDuration() {
    return timeDuration;
  }
  public String getTitle() {
    return title;
  }
  public String getPageCategory() {
    return pageCategory;
  }
  public String getLinkToArticle() {
    return linkToArticle;
  }
  public String getCategory() {
    return category;
  }
  public String getDescription() {
    return description;
  }
  public String getSource() {
    return source;
  }
  public String getAuthor() {
    return author;
  }
  public String toString() {

    return getTitle();
  }
  public String getThumbnail() {
    return thumbnail;
  }
}