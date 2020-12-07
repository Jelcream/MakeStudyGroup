package kr.ac.sunmoon.makestudygroup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import static kr.ac.sunmoon.makestudygroup.R.drawable.more;
import static kr.ac.sunmoon.makestudygroup.R.drawable.remenu;

public class LayoutMain extends AppCompatActivity {
    private Fragment lobby, chattingRooms;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;//펼치기

    private FloatingActionButton writeButton, logoutButton;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_main);
//        Intent intent = new Intent(getApplicationContext(), Lobby.class);
//        startActivity(intent);
        Intent intent = getIntent();
        user = MyUser.getInstance().getUser();
        bottomNavigationView = findViewById(R.id.bottomNavi);
        fab = findViewById(R.id.floatingActionButton);
        writeButton = findViewById(R.id.writebutton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writePost();
            }
        });
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        // 프래그먼트 생성
        lobby = new Lobby();
        chattingRooms = new ChattingRooms();
        //제일 처음 보여줄 뷰 세팅
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,lobby).commitAllowingStateLoss();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_one://lobby 화면
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, lobby).commitAllowingStateLoss();
                        fab.setVisibility(0);
                        return true;
                    case R.id.action_two://chatting_rooms 화면
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, chattingRooms).commitAllowingStateLoss();
                        fab.setVisibility(8);
                        return true;
                    case R.id.action_three://option 화면
                        fab.setVisibility(8);
                        return true;
                }
                return false;
            }
        });
    }

    boolean floatingButton = false;
    public void onClickFloat(View v){

        if(!floatingButton){
            fab.setImageResource(R.drawable.re_menu);
            writeButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.VISIBLE);
            floatingButton = true;
        }else if(floatingButton){
            fab.setImageResource(R.drawable.more_menu);
            writeButton.setVisibility(View.INVISIBLE);
            logoutButton.setVisibility(View.INVISIBLE);
            floatingButton = false;
        }
    }

    public void writePost(){
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
