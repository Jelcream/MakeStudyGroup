package kr.ac.sunmoon.makestudygroup;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PostItem implements Serializable {
    private String title, author;
    private String contents;
    private String uid, authorUid;
    private String image;
    public Map<Object, String> stars = new HashMap<>();
    public PostItem(String title, String author, String contents, String uid, String authorUid, String image){
        this.title= title;
        this.author = author;
        this.contents = contents;
        this.uid = uid;
        this.authorUid = authorUid;
        this.image = image;
    }
    public PostItem(){}

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getImage(){
        return image;
    }
    public void setTitle(String str){
        title = str;
    }
    public void setAuthor(String auth){
        author = auth;
    }
    public void setImage(String im){
        image = im;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthorUid() {
        return authorUid;
    }

    public void setAuthorUid(String authorUid) {
        this.authorUid = authorUid;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    public Map<Object, String> toMap(){
        Map<Object, String> result = new HashMap<>();
        result.put("uid", this.uid);
        result.put("author", this.author);
        result.put("authorUid", this.authorUid);
        result.put("contents", this.contents);
        result.put("title", this.title);
        result.put("images", this.image);
        return result;
    }
}
