package ProjectArticle;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;

public class Article {
  private Date date;
  private String title;
  private String linkToArticle;
  private String category;
  private String description;
  private String source;
  private String author;

  public Article(Date date, String title, String linkToArticle, String category, String description, String source, String author) {
    setDate(date);
    setTitle(title);
    setLinkToArticle(linkToArticle);
    setCategory(category);
    setDescription(description);
    setSource(source);
    setAuthor(author);
  }

  public Article(){
    setDate(new Timestamp(System.currentTimeMillis()));
    setTitle("");
    setLinkToArticle("");
    setCategory("");
    setDescription("");
    setSource("");
    setAuthor("");
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getTimeAgo() {
    long timeDiff = new Timestamp(System.currentTimeMillis()).getTime() - this.date.getTime();
    System.out.println(this.date.getTime());

    long diffSeconds = TimeUnit.MILLISECONDS.toSeconds(timeDiff)% 60;

    long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff)% 60;

    long diffHours = TimeUnit.MILLISECONDS.toHours(timeDiff)% 24;

    long diffDays = TimeUnit.MILLISECONDS.toDays(timeDiff)% 365;

    long diffYears = TimeUnit.MILLISECONDS.toDays(timeDiff)/ 365l;
    
    if (diffYears>0) {
      return String.valueOf(diffYears) + " year(s) ago";
    } else if (diffDays>0) {
      return String.valueOf(diffDays) + " day(s) ago";
    } else if (diffHours>0) {
      return String.valueOf(diffHours) + " hour(s) ago";
    } else if (diffMinutes>0) {
      return String.valueOf(diffMinutes) + " minute(s) ago";
    } else {
      return String.valueOf(diffSeconds) + " second(s) ago";
    }
  }

  public void setTitle(String title) {
    this.title = title;
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

  public Date getDate() {
    return this.date;
  }

  public String getTitle() {
    return this.title;
  }

  public String getLinkToArticle() {
    return this.linkToArticle;
  }

  public String getCategory() {
    return this.category;
  }

  public String getDescription() {
    return this.description;
  }

  public String getSource() {
    return this.source;
  }

  public String getAuthor() {
    return this.author;
  }

  public String toString() {
    return this.getTitle();
  }
}