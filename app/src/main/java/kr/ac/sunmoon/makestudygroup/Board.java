package kr.ac.sunmoon.makestudygroup;

public class Board {
    String writer_email;
    String title;
    String intro;
    String content;
    private String img;

    public Board(){

    }
    public Board(String writer_email, String title, String intro, String conttent) {
        this.writer_email = writer_email;
        this.title = title;
        this.intro = intro;
        this.content = content;
    }

    public String getWriter_email() {
        return writer_email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public void setWriter_email(String writer_email) {
        this.writer_email = writer_email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
