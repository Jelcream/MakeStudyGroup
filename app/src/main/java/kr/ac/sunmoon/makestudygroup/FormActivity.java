package kr.ac.sunmoon.makestudygroup;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

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


    private Uri mDownloadImageUri; //프로필사진 스토리지 URI
    private String mTmpDownloadImageUri; //Shared에서 받아올떄 String형이라 임시로 받아오는데 사용
    private Bitmap img; //비트맵 프로필사진 (이걸.
    private StorageReference mStorageRef; //파이어베이스 스토리지
    private StorageReference mProfileRef; //프로필이미지 담을 파베 스토리

    //RequestCode
    final static int PICK_IMAGE = 1; //갤러리에서 사진선택
    final static int CAPTURE_IMAGE = 2;  //카메라로찍은 사진선택
    private String mCurrentPhotoPath; //카메라로 찍은 사진 저장할 루트경로\
    FirebaseFirestore db; //파이어스토어 디비

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
        setContentView(R.layout.form_popup);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddHHMMSS");
        Date date = new Date();

        edit = findViewById(R.id.edit_content);
        title = findViewById(R.id.edit_title);

        dateStr = simpleDateFormat.format(date);

        Intent intent = getIntent();
        User user = MyUser.getInstance().getUser();
        editor = user.getName();
        editorUid = user.getUid();


    }
    public void addContent(View v){
        FirebaseDatabase mDatabase =FirebaseDatabase.getInstance();
        DatabaseReference DBref = mDatabase.getReference("Post");
        HashMap<Object,String> hashmap = new HashMap<Object,String>();
        titleStr = title.getText().toString().trim();
        contents = edit.getText().toString();
        if(titleStr.equals("")||titleStr == null||contents.equals("") || contents == null){
            Toast.makeText(getApplicationContext(),"내용을 채워주세요",Toast.LENGTH_LONG).show();
            return;
        }
        hashmap.put("title", titleStr);
        hashmap.put("author", editor);
        hashmap.put("contents", contents);
        hashmap.put("uid", editorUid+"-"+dateStr);
        hashmap.put("authorUid", editorUid);
        DBref.child(editorUid+"-"+dateStr).setValue(hashmap);
        //데이터베이스에 등록
        finish();
    }

    public void addImg(View v){

        photoDialogRadio();

        finish();
    }
    //사진찍기 or 앨범에서 가져오기 선택 다이얼로그
    private void photoDialogRadio() {
        final CharSequence[] PhotoModels = {"갤러리에서 가져오기", "카메라로 촬영 후 가져오기", "기본사진으로 하기"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        //alt_bld.setIcon(R.drawable.icon);
        alt_bld.setTitle("배경사진 설정");
        alt_bld.setSingleChoiceItems(PhotoModels, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(FormActivity.this, PhotoModels[item] + "가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                if (item == 0) { //갤러리
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_IMAGE);
                } else if (item == 1) { //카메라찍은 사진가져오기
                    //takePictureFromCameraIntent();
                } else { //기본화면으로하기
                    //mPhotoCircleImageView.setImageResource(R.drawable.profile);
                    img = null;
                    mTmpDownloadImageUri = null;
                }
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }
}
