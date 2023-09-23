package vn.edu.usth.arxiv;

public class ArticleModel {
    String articleTitle;
    String articleDate;
    String articleAuthor;


    public ArticleModel(String articleTitle, String articleDate, String articleAuthor) {
        this.articleTitle = articleTitle;
        this.articleDate = articleDate;
        this.articleAuthor = articleAuthor;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }
}
