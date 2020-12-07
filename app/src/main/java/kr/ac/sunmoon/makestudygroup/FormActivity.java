package kr.ac.sunmoon.makestudygroup;

import android.Manifest;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FormActivity extends Activity {
    TextView edit, title;
    String titleStr;
    String contents;
    String editor;
    String dateStr;
    String editorUid;

    private static final int GALLERY_CODE = 10;
    ImageView imageView;
    private String imagePath;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
        setContentView(R.layout.form_popup);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
        Date date = new Date();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        edit = findViewById(R.id.edit_content);
        title = findViewById(R.id.edit_title);

        dateStr = simpleDateFormat.format(date);

        Intent intent = getIntent();
        User user = MyUser.getInstance().getUser();
        editor = user.getName();
        editorUid = user.getUid();


    }
    public void addContent(View v){
        final FirebaseDatabase mDatabase =FirebaseDatabase.getInstance();
        final DatabaseReference DBref = mDatabase.getReference("Post");
        final DatabaseReference DBGroup = mDatabase.getReference("Group");
        final DatabaseReference DBUsRO = mDatabase.getReference("UserRoom");
        final String Uid = editorUid+"-"+dateStr;
        final User user = MyUser.getInstance().getUser();
        final HashMap<Object,String> UsRoMap, groupMap,groupUserListMap,hashmap = new HashMap<Object,String>();
        groupMap = new HashMap<>();
        groupUserListMap = new HashMap<>();
        UsRoMap = new HashMap<>();

        titleStr = title.getText().toString().trim();
        contents = edit.getText().toString();
        if(titleStr.equals("")||titleStr == null||contents.equals("") || contents == null){
            Toast.makeText(getApplicationContext(),"내용을 채워주세요",Toast.LENGTH_LONG).show();
            return;
        }

        final Uri file = Uri.fromFile(new File(imagePath));

        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://makestudygroup-5eb3a.appspot.com");
        final StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        /*
        final UploadTask uploadTask = riversRef.putFile(file);
        /*

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
               }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

                @SuppressWarnings("VisibleForTests")
                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                image_uri[0] = downloadUrl.toString();


                image_uri[0] = riversRef.getDownloadUrl().toString();

                hashmap.put("image", );
                hashmap.put("title", titleStr);
                hashmap.put("author", editor);
                hashmap.put("contents", contents);
                hashmap.put("uid", editorUid+"-"+dateStr);
                hashmap.put("authorUid", editorUid);
                DBref.child(Uid).setValue(hashmap);
            }
        });
*/

        riversRef.putFile(file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                final Task<Uri> imageUri = task.getResult().getStorage().getDownloadUrl();
                while(!imageUri.isComplete());

                hashmap.put("image", imageUri.getResult().toString());
                hashmap.put("title", titleStr);
                hashmap.put("author", editor);
                hashmap.put("contents", contents);
                hashmap.put("uid", editorUid+"-"+dateStr);
                hashmap.put("authorUid", editorUid);
                DBref.child(Uid).setValue(hashmap);
            }
        });

        groupMap.put("GroupTitle", titleStr);
        groupMap.put("GroupID", Uid);
        DBGroup.child(Uid).setValue(groupMap);

        groupUserListMap.put("name", user.getName());
        groupUserListMap.put("email",user.getEmail());
        DBGroup.child(Uid).child("UserList").child(user.getUid()).setValue(groupUserListMap);

        UsRoMap.put(dateStr, Uid);
        DBUsRO.child(user.getUid()).setValue(UsRoMap);
        //데이터베이스에 등록
        finish();
    }

    public void addImg(View v){
        photoDialogRadio();
    }
    //사진찍기 or 앨범에서 가져오기 선택 다이얼로그
    private void photoDialogRadio() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        startActivityForResult(intent, GALLERY_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == GALLERY_CODE){
            imagePath = getPath(data.getData());
            //imageView.setImageURI(Uri.fromFile(file));

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
