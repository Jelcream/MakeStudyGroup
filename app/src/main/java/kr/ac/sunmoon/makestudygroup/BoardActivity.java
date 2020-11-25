package kr.ac.sunmoon.makestudygroup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Board> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    FloatingActionButton btnAdd, btnFind;
    //DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Intent it = getIntent();
        final String email = it.getStringExtra("email");
        final String pass = it.getStringExtra("pass");
        /*
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false); //레이아웃매니저 생성
        mRecyclerView.setLayoutManager(layoutManager); ////만든 레이아웃매니저 객체를(설정을) 리사이클러 뷰에 설정해줌
        //mRecyclerView.setAdapter(mFirebaseAdapter);

        myAdapter = new MyAdapter(this, getMyList(email));
        mRecyclerView.setAdapter(myAdapter);
*/

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();  //firebase connected

        databaseReference = database.getReference("Board");  //DBtable connected
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어 베이스 데이터베이스의 데이터 받아오는 곳
                arrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Board board = snapshot.getValue(Board.class);
                    arrayList.add(board); //데이터들을 배열 리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); //새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비 가져올때 에러 발생시
                Log.e("BoardActivity", String.valueOf(error.toException()));    //print error
            }
        });
        adapter = new BoardAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);   //Adapter - RecyclerView connected

        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnFind = (FloatingActionButton) findViewById(R.id.btnFind);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("pass", pass);
                startActivity(intent);*/
            }
        });

    }

    private ArrayList<Board> getMyList(String email){
        final ArrayList<Board> models = new ArrayList<>();
/*
        Model m = new Model();
        m.setTitle("Title4");
        m.setContents("Contents");
        m.setImg(R.drawable.mov04);
        models.add(m);
        Model m2 = new Model();
        m2.setTitle("Title4");
        m2.setContents("Contents");
        m2.setImg(R.drawable.mov04);
        models.add(m2);
        Model m3 = new Model();
        m3.setTitle("Title4");
        m3.setContents("Contents");
        m3.setImg(R.drawable.mov04);
        models.add(m3);
*/

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference node = root.child("Board");

        node.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    int index = 0;
                    String value="";
                    Board b = new Board();
                    for(DataSnapshot ds2 : ds.getChildren()){
                        if(index == 0){
                            //value += ds2.getValue();
                            //b.setImg(R.drawable.mov03);
                        }else if(index == 1){
                            value += ds2.getValue();
                            b.setIntro(value);
                        }else if(index == 2){
                            value += ds2.getValue();
                            b.setTitle(value);
                        }else if(index == 3){
                            value += ds2.getValue();
                            b.setWriter_email(value);
                        }else{
                            break;
                        }
                        models.add(b);
                        value = "";
                        index++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return models;
    }
}
