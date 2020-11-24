package kr.ac.sunmoon.makestudygroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment lobby, chattingRooms;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(getApplicationContext(), Lobby.class);
//        startActivity(intent);

        bottomNavigationView = findViewById(R.id.bottomNavi);

        // 프래그먼트 생성
        lobby = new Lobby();
        chattingRooms = new ChattingRooms();
        //제일 처음 보여줄 뷰 세팅
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,lobby).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_one://lobby 화면
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, lobby).commitAllowingStateLoss();
                        return true;
                    case R.id.action_two://chatting_rooms 화면
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, chattingRooms).commitAllowingStateLoss();
                        return true;
                    case R.id.action_three://option 화면
                        return true;
                }
                return false;
            }
        });
    }
}