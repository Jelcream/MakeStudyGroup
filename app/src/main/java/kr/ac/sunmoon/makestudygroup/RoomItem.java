package kr.ac.sunmoon.makestudygroup;

public class RoomItem {
    private int image;
    private String title;
    private String id;
    public  RoomItem(String ti, int im){
        this.title = ti;
        this.image = im;
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
}
