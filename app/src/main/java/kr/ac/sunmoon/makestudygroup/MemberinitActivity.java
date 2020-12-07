package kr.ac.sunmoon.makestudygroup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;

public class MemberinitActivity extends AppCompatActivity {
    private static final String TAG ="MemberinitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);

        findViewById(R.id.checkButton).setOnClickListener(onClickListener);
        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.checkButton:
                    profileUpdate();
                    break;
                case R.id.loginButton:
                    myStartActivity(LoginActivity.class);
                    break;
            }
        }
    };

    private void profileUpdate() {
        String name = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
        String phoneNumber = ((EditText) findViewById(R.id.phoneNumberEditText)).getText().toString();
        String birthDay = ((EditText) findViewById(R.id.birthDayEditText)).getText().toString();
        String address = ((EditText) findViewById(R.id.addressEditText)).getText().toString();

        if (name.length() > 0 && phoneNumber.length() > 9 && birthDay.length() > 5 && address.length() > 0) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            MemberInfo memberInfo = new MemberInfo(name,phoneNumber,birthDay,address);

            if(user != null) {
                HashMap<Object,String> hashMap = new HashMap<>();
                String uid = user.getUid();
                hashMap.put("name",name);
                hashMap.put("phone",phoneNumber);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Users");
                reference.child(uid).child("name").setValue(name);
                reference.child(uid).child("phone").setValue(phoneNumber);

                Toast.makeText(getApplicationContext(), "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            startToast("회원정보를 입력해주세요");
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}