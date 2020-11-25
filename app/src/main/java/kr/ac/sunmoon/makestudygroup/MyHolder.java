package kr.ac.sunmoon.makestudygroup;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder {

    ImageView mImaeView;
    TextView textView,contents, writer;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        this.mImaeView = itemView.findViewById(R.id.img);
        this.textView = itemView.findViewById(R.id.title);
        this.contents = itemView.findViewById(R.id.content);
        this.writer = itemView.findViewById(R.id.writer);
    }
}
