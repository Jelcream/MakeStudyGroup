package kr.ac.sunmoon.makestudygroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FormActivity extends Activity {
    TextView edit, title;
    String titleStr;
    String contents;
    String editor;
    String dateStr;
    String editorUid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
        setContentView(R.layout.form_popup);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
        Date date = new Date();

        edit = findViewById(R.id.edit_content);
        title = findViewById(R.id.edit_title);

        dateStr = simpleDateFormat.format(date);

        Intent intent = getIntent();
        User user = MyUser.getInstance().getUser();
        editor = user.getName();
        editorUid = user.getUid();


    }
    public void addContent(View v){
        FirebaseDatabase mDatabase =FirebaseDatabase.getInstance();
        DatabaseReference DBref = mDatabase.getReference("Post");
        DatabaseReference DBGroup = mDatabase.getReference("Group");
        DatabaseReference DBUsRO = mDatabase.getReference("UserRoom");
        String Uid = editorUid+"-"+dateStr;
        User user = MyUser.getInstance().getUser();
        HashMap<Object,String> UsRoMap, groupMap,groupUserListMap,hashmap = new HashMap<Object,String>();
        groupMap = new HashMap<>();
        groupUserListMap = new HashMap<>();
        UsRoMap = new HashMap<>();

        titleStr = title.getText().toString().trim();
        contents = edit.getText().toString();
        if(titleStr.equals("")||titleStr == null||contents.equals("") || contents == null){
            Toast.makeText(getApplicationContext(),"내용을 채워주세요",Toast.LENGTH_LONG).show();
            return;
        }
        hashmap.put("title", titleStr);
        hashmap.put("author", editor);
        hashmap.put("contents", contents);
        hashmap.put("uid", editorUid+"-"+dateStr);
        hashmap.put("authorUid", editorUid);
        DBref.child(Uid).setValue(hashmap);

        groupMap.put("GroupTitle", titleStr);
        groupMap.put("GroupID", Uid);
        DBGroup.child(Uid).setValue(groupMap);

        groupUserListMap.put("name", user.getName());
        groupUserListMap.put("email",user.getEmail());
        DBGroup.child(Uid).child("UserList").child(user.getUid()).setValue(groupUserListMap);

        UsRoMap.put(dateStr, Uid);
        DBUsRO.child(user.getUid()).setValue(UsRoMap);

        //데이터베이스에 등록
        finish();
    }
}
