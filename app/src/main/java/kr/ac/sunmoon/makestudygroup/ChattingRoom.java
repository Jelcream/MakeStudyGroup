package kr.ac.sunmoon.makestudygroup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChattingRoom extends AppCompatActivity {
    TextView content;
    ArrayList<Chat> chats;
    String roomUid;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutmanager;
    RecyclerView.Adapter mAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatRef, userListRef;
    Date date;
    SimpleDateFormat sdfUid, sdf;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_room);
        chats = new ArrayList<>();
        Intent intent = getIntent();
        content = findViewById(R.id.chat_edit_text);
        firebaseDatabase = FirebaseDatabase.getInstance();
        sdfUid = new SimpleDateFormat("yyyyMMddHHmmSSSS");
        sdf = new SimpleDateFormat("HH:mm");
        roomUid = intent.getExtras().getString("Uid");
        chatRef = firebaseDatabase.getReference("Group").child(roomUid).child("Chats");

        recyclerView = (RecyclerView) findViewById(R.id.chats);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutmanager);

        mAdapter = new ChatAdapter(getApplicationContext(),chats);
        recyclerView.setAdapter(mAdapter);

        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("test value", snapshot.getKey());
                chats.add(snapshot.getValue(Chat.class));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void sendText(View view){
        String text = content.getText().toString();
        User user = MyUser.getInstance().getUser();
        Date date = new Date();
        String Uid = sdfUid.format(date);
        String dateStr = sdf.format(date);
        String editor = user.getName();

        HashMap<Object, String> map = new HashMap<>();

        map.put("author", editor);
        map.put("uid",Uid);
        map.put("date", dateStr);
        map.put("content",text);
        content.setText("");
        chatRef.child(Uid).setValue(map);
    }
}
