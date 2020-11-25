package kr.ac.sunmoon.makestudygroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteActivity extends AppCompatActivity {

    EditText titleBox, introBox, contentBox;
    Button writeButton, cancleButton;
    Button uploadImg;
    FirebaseDatabase firebasedb;
    DatabaseReference root;
    DatabaseReference childRef2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Intent it = getIntent();
        final String email = it.getStringExtra("email");
        final String pass = it.getStringExtra("pass");

        firebasedb = FirebaseDatabase.getInstance();
        root = firebasedb.getReference();

        childRef2 = root.child("Board");

        titleBox = (EditText) findViewById(R.id.titlebox);
        introBox = (EditText) findViewById(R.id.introBox);
        contentBox = (EditText) findViewById(R.id.contentBox);

        uploadImg = (Button) findViewById(R.id.uploadImg);
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        writeButton = (Button) findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                database.child("board").child(email).child("title").setValue(titleBox.getText().toString());
                database.child("board").child(email).child("intro").setValue(introBox.getText().toString());
                database.child("board").child(email).child("content").setValue(contentBox.getText().toString());
                 */
                DatabaseReference msgNode = childRef2.push();
                msgNode.setValue(new Board(email, titleBox.getText().toString(), introBox.getText().toString(), contentBox.getText().toString()));
                finish();
            }
        });



        cancleButton = (Button) findViewById(R.id.cancleButton);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
