package kr.ac.sunmoon.makestudygroup;

<<<<<<< HEAD
import android.content.Intent;
=======
>>>>>>> jelcream
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText editPasswd, editName, editPhone, editEmail;
    Button btnRegister, btnCancel;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        editPasswd = (EditText) findViewById(R.id.editPasswd);
        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editEmail = (EditText) findViewById(R.id.editEmail);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public  void register(){
<<<<<<< HEAD
        String passwd = editPasswd.getText().toString();
=======
        final String passwd = editPasswd.getText().toString();
>>>>>>> jelcream
        String email = editEmail.getText().toString();

        if(passwd.equals("") || email.equals("")){
            Toast.makeText(this, "모두 채워 주세요", Toast.LENGTH_SHORT).show();
        }else{
            final HashMap<Object, String> hashMap = new HashMap<>();
            hashMap.put("passwd", passwd);
            hashMap.put("email", email);

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
<<<<<<< HEAD
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        String emails = user.getEmail();
                        String uid = user.getUid();
                        String name = editName.getText().toString();
                        String phone = editPhone.getText().toString();
                        HashMap<Object,String> hashMap = new HashMap<>();

                        hashMap.put("uid",uid);
                        hashMap.put("email",emails);
                        hashMap.put("name",name);
                        hashMap.put("phone",phone);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("Users");
                        reference.child(uid).setValue(hashMap);

                        Toast.makeText(getApplicationContext(), "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
=======
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                String emails = user.getEmail();
                                String uid = user.getUid();
                                String name = editName.getText().toString();
                                String phone = editPhone.getText().toString();
                                HashMap<Object,String> hashMap = new HashMap<>();

                                hashMap.put("uid",uid);
                                hashMap.put("email",emails);
                                hashMap.put("name",name);
                                hashMap.put("phone",phone);
                                hashMap.put("pw",passwd);

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);

                                Toast.makeText(getApplicationContext(), "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
>>>>>>> jelcream
        }
    }
}
