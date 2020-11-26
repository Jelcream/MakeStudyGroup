package kr.ac.sunmoon.makestudygroup;

public class MyUser extends User{
    private static MyUser myUser = new MyUser();
    private MyUser(){super();}
    public static MyUser getInstance(){
        return myUser;
    }
    public void setUser(User u){
        setName(u.getName());
        setEmail(u.getEmail());
        setPhone(u.getPhone());
        setPw(u.getPw());
        setName(u.getName());
        setUid(u.getUid());
    }
    public User getUser(){
        return (User) myUser;
    }
}
