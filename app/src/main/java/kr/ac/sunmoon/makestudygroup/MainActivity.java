package kr.ac.sunmoon.makestudygroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.transition.Visibility;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    TextView input_id, input_pw;
    Button loginButton, registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        mAuth = FirebaseAuth.getInstance();

        input_id = (TextView) findViewById(R.id.inputBox_id);
        input_pw = (TextView) findViewById(R.id.inputBox_pw);

        loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runLogin();
            }
        });

        registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    private void register() {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    public void runLogin(){
        final String email = input_id.getText().toString();
        final String pass = input_pw.getText().toString();
        if(email == null || email.equals("")){
            Toast.makeText(getApplicationContext(), "please input your email", Toast.LENGTH_SHORT).show();
            return;
        }else if(pass == null || pass.equals("")){
            Toast.makeText(getApplicationContext(), "please input your passwd", Toast.LENGTH_SHORT).show();
            return;
        }
        //인증 절
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {    //로그인 결과 : task
                if(task.isSuccessful()){
                    final FirebaseUser user = mAuth.getCurrentUser();
                    HashMap<Object,String> map = new HashMap<>();
                    map.put("on", "true");
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    final ArrayList<User> userItem = new ArrayList<User>();
                    databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Log.e("add test Snapshot", snapshot.getKey());
                            MyUser myUser = MyUser.getInstance();
                            if(snapshot.getKey().equals(user.getUid())) {
                                myUser.setUser(snapshot.getValue(User.class));
                                Log.e("setting my user", "complete");
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Log.e("change test Snapshot", snapshot.getKey());
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

                    Log.e("Database test", user.getUid());
                    //Toast.makeText(getApplicationContext(), "Login Success",Toast.LENGTH_SHORT).show();
                    finish();
                    //startActivity(new Intent(getApplicationContext(), BoardActivity.class));
                    Intent intent = new Intent(getApplicationContext(), LayoutMain.class);
                    Log.e("Database test", "complete");
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}