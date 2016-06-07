package jason.xie.columns.model;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class Article {
    public Author author;
    public String title;
    public String titleImage;
    public String slug;
    public int commentsCount;
    public int likesCount;

    public Article(){

    }

    public  Article(Author author, String title, String titleImage, String slug, int commentsCount, int likesCount){
        this.author = author;
        this.title = title;
        this.titleImage = titleImage;
        this.slug = slug;
        this.commentsCount = commentsCount;
        this.likesCount = likesCount;
    }
}
