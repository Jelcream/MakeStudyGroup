package kr.ac.sunmoon.makestudygroup;

import androidx.core.util.ObjectsCompat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String name, uid, phone, email, pw;
    Map<Object, String> map = new HashMap<>();

    public User(String name, String uid, String phone, String email, String pw){
        this.name = name;
        this.uid = uid;
        this.phone = phone;
        this.email = email;
        this.pw = pw;
    }
    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Map<Object, String> toMap(){
        Map<Object, String> result = new HashMap<>();
        result.put("name", this.name);
        result.put("uid", this.uid);
        result.put("phone",this.phone);
        result.put("email",this.email);
        result.put("pw",this.pw);
        return result;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
