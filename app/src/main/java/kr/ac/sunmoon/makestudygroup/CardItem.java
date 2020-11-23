package kr.ac.sunmoon.makestudygroup;

import android.widget.ImageView;

public class CardItem {
    private String title, author;
    private int image;
    public CardItem(String title, String author, int im){
        this.title= title;
        this.author = author;
        this.image = im;
    }
    String getTitle(){
        return title;
    }
    String getAuthor(){
        return author;
    }
    int getImage(){
        return image;
    }
    void setTitle(String str){
        title = str;
    }
    void setAuthor(String auth){
        author = auth;
    }
    void setImage(int im){
        image = im;
    }
}
