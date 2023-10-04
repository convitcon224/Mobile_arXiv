package vn.edu.usth.arxiv;

public class ArticleModel {
    private int articlePosition;
    private String articleID;
    private String articleTitle;
    private String articleDate;
    private String articleAuthor;


    public ArticleModel(int articlePosition, String id, String articleTitle, String articleDate, String articleAuthor) {
        this.articlePosition = articlePosition;
        this.articleID = id;
        this.articleTitle = articleTitle;
        this.articleDate = articleDate;
        this.articleAuthor = articleAuthor;
    }

    public String getArticlePosition() {
        return String.valueOf(articlePosition);
    }

    public String getArticleID() {
        return articleID;
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
