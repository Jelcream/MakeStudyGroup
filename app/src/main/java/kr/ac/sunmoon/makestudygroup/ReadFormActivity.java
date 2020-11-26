package kr.ac.sunmoon.makestudygroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ReadFormActivity extends Activity {
    TextView content, title;
    TextView editor;
    PostItem post;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
        setContentView(R.layout.read_form);

        content = findViewById(R.id.read_contentView);
        title = findViewById(R.id.read_titleView);
        editor = findViewById(R.id.read_authorView);

        Intent intent = getIntent();
        post = (PostItem) intent.getSerializableExtra("Post");

        title.setText(post.getTitle());
        content.setText(post.getContents());
        editor.setText(post.getAuthor());

    }
    public void registTheRoom(View view){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference UsRoDB = firebaseDatabase.getReference("UserRoom");
        DatabaseReference GroupDB = firebaseDatabase.getReference("Group");
        String dateStr;
        User user = MyUser.getInstance().getUser();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        dateStr = sdf.format(date);

        HashMap<Object,String> groupMap, userRoomMap;
        groupMap = new HashMap<>();
        userRoomMap = new HashMap<>();

        groupMap.put("name", user.getName());
        groupMap.put("email",user.getEmail());
        GroupDB.child(post.getUid()).child("UserList").child(user.getUid()).setValue(groupMap);

        userRoomMap.put(dateStr, post.getUid());
        UsRoDB.child(user.getUid()).setValue(userRoomMap);

        finish();
    }
}
