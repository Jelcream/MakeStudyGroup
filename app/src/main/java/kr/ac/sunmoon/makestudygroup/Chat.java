package kr.ac.sunmoon.makestudygroup;

public class Chat {
    private String id;
    private String content;
    private String author;
    private String date;
    public Chat(){}
    public Chat(String id, String contents, String author, String date){
        this.id = id;
        this.content = content;
        this.author = author;
        this.date = date;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContents(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
