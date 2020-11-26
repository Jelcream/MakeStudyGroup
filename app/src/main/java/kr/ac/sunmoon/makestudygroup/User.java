package kr.ac.sunmoon.makestudygroup;

<<<<<<< HEAD
public class User {
    private String id;
    private String passwd;
    private String name;
    private String phone;
    private String email;

    public User(){}

    public User(String id, String passwd, String name, String phone, String email) {
        this.id = id;
        this.passwd = passwd;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
=======
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
>>>>>>> jelcream

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

<<<<<<< HEAD
=======
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

>>>>>>> jelcream
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
<<<<<<< HEAD
=======
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
>>>>>>> jelcream
}
