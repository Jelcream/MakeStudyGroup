package kr.ac.sunmoon.makestudygroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //CardView setting
    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutmanager;
    String title[], author[];
    int img[];
    String s1[], s2[];
    //CardView setting end
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //cardView Test Start
        recyclerView = findViewById(R.id.recyclerview);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        mLayoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutmanager);
        title = new String[10];
        author = new String[10];
        img = new int[10];

        for(int i = 0 ; i < 10 ; i++){
            title[i] = "title"+i;
        }
        for(int i = 0; i < 10; i++){
            author[i] = "author"+i;
        }
        for(int i = 0; i< 10; i++){
            img[i] = R.mipmap.ic_launcher;
        }

        mAdapter = new MyAdapter(getApplicationContext(), title, author, img);
        recyclerView.setAdapter(mAdapter);
        //cardView Test End
    }
}