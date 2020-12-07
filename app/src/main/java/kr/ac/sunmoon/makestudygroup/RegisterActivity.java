package kr.ac.sunmoon.makestudygroup;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText editPasswd, editName, editPhone, editEmail;
    Button btnRegister, btnCancel;
    ImageView profile;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    StorageReference storageRef;
    private static final int GALLERY_CODE = 10;
    ImageView imageView;
    private String imagePath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageRef = firebaseStorage.getReferenceFromUrl("gs://makestudygroup-5eb3a.appspot.com");

        editPasswd = (EditText) findViewById(R.id.editPasswd);
        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editEmail = (EditText) findViewById(R.id.editEmail);
        profile = (ImageView) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

                startActivityForResult(intent, GALLERY_CODE);
            }
        });

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
        final String passwd = editPasswd.getText().toString();
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
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                final String[] profile_uri = new String[1];
                                String emails = user.getEmail();
                                String uid = user.getUid();
                                String name = editName.getText().toString();
                                String phone = editPhone.getText().toString();
                                final HashMap<Object,String> hashMap = new HashMap<>();
                                Uri file = Uri.fromFile(new File(imagePath));
                                StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
                                riversRef.putFile(file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                   @Override
                                   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                       final Task<Uri> profileUri = task.getResult().getStorage().getDownloadUrl();

                                       while(!profileUri.isComplete());

                                       profile_uri[0] = profileUri.getResult().toString();
                                   }
                               });
                                hashMap.put("profile", profile_uri[0]);
                                System.out.println("profile >>> "+ profile_uri[0]);
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
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE) {
            String path = getPath(data.getData());
            imagePath = path;
            File file = new File(path);
            profile.setImageURI(Uri.fromFile(file));

            //upload(path);
        }
    }
    public String getPath(Uri uri){
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj,null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }
}
