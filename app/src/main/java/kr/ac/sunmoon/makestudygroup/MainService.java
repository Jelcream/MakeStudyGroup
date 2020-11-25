package kr.ac.sunmoon.makestudygroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainService extends AppCompatActivity {
    TextView textView;
    Button logoutButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);

        mAuth = FirebaseAuth.getInstance();

        textView = (TextView) findViewById(R.id.textView);
        logoutButton = (Button) findViewById(R.id.btnLogout);

        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        textView.setText("Welcome !!!     " + currentUser.getEmail());

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLogout();
            }
        });
    }

    private void goLogout(){
        mAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
