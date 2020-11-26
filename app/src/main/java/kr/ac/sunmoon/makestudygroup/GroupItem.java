package kr.ac.sunmoon.makestudygroup;

public class GroupItem {
    private int image = R.mipmap.ic_launcher;
    private String title;
    private String id;

    public GroupItem(String ti, String id){
        this.title = ti;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public int getImage(){
        return image;
    }
    public void setTitle(String ti){
        this.title = ti;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public void setId(String id){this.id = id;}
    public String getId(){return this.id;}
}
