package kr.ac.sunmoon.makestudygroup;

public class User {
    private String id;
    private String passwd;
    private String name;

    public User(String id, String passwd, String name){
        this.id = id;
        this.passwd = passwd;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

}
