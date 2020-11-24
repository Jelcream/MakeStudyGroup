package kr.ac.sunmoon.makestudygroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;

public class FormActivity extends Activity {
    TextView edit, title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 없애기
        setContentView(R.layout.form_popup);

        edit = findViewById(R.id.edit_content);
        title = findViewById(R.id.edit_title);

        Intent intent = getIntent();

    }
    public void addContent(View v){
        //데이터베이스에 등록
        finish();
    }
}
