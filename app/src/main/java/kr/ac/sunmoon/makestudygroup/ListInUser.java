package kr.ac.sunmoon.makestudygroup;

public class ListInUser {
    private String email;
    private String name;
    public ListInUser(String email, String name){
        this.email = email;
        this.name = name;
    }
    public ListInUser(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
