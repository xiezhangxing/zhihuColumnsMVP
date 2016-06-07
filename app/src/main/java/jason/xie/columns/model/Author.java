package jason.xie.columns.model;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class Author {
    public String bio;
    public String hash;
    public String description;
    public String profileUrl;
    public Avatar avatar;
    public String slug;
    public String name;

    public Author() {

    }

    public Author(String bio, String hash, String description, String profileUrl, Avatar avatar, String slug, String name){
        this.bio = bio;
        this.hash = hash;
        this.description = description;
        this.profileUrl = profileUrl;
        this.avatar = avatar;
        this.slug = slug;
        this.name = name;
    }
}
