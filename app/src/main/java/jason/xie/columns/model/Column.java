package jason.xie.columns.model;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class Column {
    public int followersCount;
    public String description;
    public String slug;
    public String name;
    public String postsCount;
    public Avatar avatar;

    public Column(int followersCount, String description, String slug, String name, String postsCount, Avatar avatar){
        this.followersCount = followersCount;
        this.description = description;
        this.slug = slug;
        this.name = name;
        this.postsCount = postsCount;
        this.avatar = avatar;
    }

}
