package kr.ac.sunmoon.makestudygroup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class LobbyAdapter extends RecyclerView.Adapter<LobbyAdapter.MyViewHolder>{
    ArrayList<PostItem> cards;
    Context context;

    //Lobby의 어댑터
    private OnRecyItemClickListener recyListener;
    public void setRecyListener(OnRecyItemClickListener listener){
        this.recyListener = listener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, author;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(RecyclerView.NO_POSITION != pos){
                        if(recyListener != null) {
                            recyListener.onItemClick(v, pos);
                        }
                        Toast.makeText(context, "test position" + pos, Toast.LENGTH_LONG).show();
                        Log.e("Event","Touch Position "+pos);
                    }
                }
            });
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            imageView = itemView.findViewById(R.id.imageView2);
        }
    }

    public LobbyAdapter(Context c, ArrayList<PostItem> cards){
        context = c;
        this.cards = cards;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardv, parent, false);
        return new MyViewHolder(view);
    }

    //xml 값 설정 Glide를 통한 이미지 뷰 설정
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.title.setText(cards.get(position).getTitle());
        holder.author.setText(cards.get(position).getAuthor());
        Glide.with(context).load(cards.get(position).getImage()).error(R.drawable.logo).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void appendCards(String s, String s1, int img){
        cards.add(null);
    }
}
