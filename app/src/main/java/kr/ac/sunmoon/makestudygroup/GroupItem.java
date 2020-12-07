package kr.ac.sunmoon.makestudygroup;

public class GroupItem {
    private String image;
    private String title;
    private String id;

    public GroupItem(String ti, String id, String image){
        this.title = ti;
        this.id = id;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }
    public String getImage(){
        return image;
    }
    public void setTitle(String ti){
        this.title = ti;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setId(String id){this.id = id;}
    public String getId(){return this.id;}
}
