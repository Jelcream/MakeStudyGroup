package kr.ac.sunmoon.makestudygroup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.signInButton);

        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoPasswordRestButton).setOnClickListener(onClickListener);
        findViewById(R.id.signUpButton).setOnClickListener(onClickListener);

//        if (mAuth.getCurrentUser() != null) {
//            Intent intent = new Intent(getApplication(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        FirebaseAuth.getInstance().signOut();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),
                null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase.getReference();
                            Log.e("Database test", user.getUid());

                            String emails = user.getEmail();
                            String uid = user.getUid();
                            String name = user.getDisplayName();
                            String phone = user.getPhoneNumber();
                            if(phone == null) phone = "null";
                            HashMap<Object,String> hashMap = new HashMap<>();

                            hashMap.put("uid",uid);
                            hashMap.put("email",emails);
                            hashMap.put("name",name);
                            hashMap.put("phone",phone);
                            hashMap.put("pw","non pw");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            reference.child(uid).setValue(hashMap);
                            MyUser myUser = MyUser.getInstance();
                            myUser.setUid(uid);
                            myUser.setName(name);
                            myUser.setEmail(emails);
                            myUser.setPhone(phone);
                            myUser.setPw("non pw");
                            //Toast.makeText(getApplicationContext(), "Login Success",Toast.LENGTH_SHORT).show();
                            finish();
                            //startActivity(new Intent(getApplicationContext(), BoardActivity.class));
                            updateUI(user);
                        }else{
                            Toast.makeText(getApplicationContext(), "Login Failed",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            Intent intent = new Intent(this, LayoutMain.class);
            startActivity(intent);
            finish();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginButton:
                    login();
                    break;
                case R.id.signUpButton:
                    myStartActivity(SignUpActivity.class);
                    break;
                case R.id.gotoPasswordRestButton:
                    myStartActivity(PasswordResetActivity.class);
                    break;
            }
        }
    };

    private void login() {
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

        if (email.length() > 0 && password.length() > 0) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                final FirebaseUser user = mAuth.getCurrentUser();
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
                                updateUI(user);
                            }else{
                                Toast.makeText(getApplicationContext(), "Login Failed",Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });

        } else {
            startToast("이메일 또는 비밀번호를 입력해주세요!");
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

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}