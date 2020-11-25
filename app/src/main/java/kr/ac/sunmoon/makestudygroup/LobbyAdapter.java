package kr.ac.sunmoon.makestudygroup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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



    public LobbyAdapter(Context c, String s[], String s1[], int img[]){
        context = c;
        cards = new ArrayList<PostItem>();
        for(int i = 0; i < s.length; i++){
            cards.add(new PostItem(s[i], s1[i], img[i]));
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(cards.get(position).getTitle());
        holder.author.setText(cards.get(position).getAuthor());
        holder.imageView.setImageResource(cards.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void appendCards(String s, String s1, int img){
        cards.add(new PostItem(s,s1,img));
    }
}
