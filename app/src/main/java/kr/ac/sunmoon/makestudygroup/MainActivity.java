package kr.ac.sunmoon.makestudygroup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
        if(email == null){
            Toast.makeText(getApplicationContext(), "please input your email", Toast.LENGTH_SHORT).show();
            return;
        }else if(pass == null){
            Toast.makeText(getApplicationContext(), "please input your passwd", Toast.LENGTH_SHORT).show();
            return;
        }
    //인증 절
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {    //로그인 결과 : taskㅇ
                if(task.isSuccessful()){
                    //Toast.makeText(getApplicationContext(), "Login Success",Toast.LENGTH_SHORT).show();
                    finish();
                    //startActivity(new Intent(getApplicationContext(), BoardActivity.class));
                    Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("pass", pass);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}