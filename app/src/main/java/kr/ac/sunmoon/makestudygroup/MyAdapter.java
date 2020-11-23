package kr.ac.sunmoon.makestudygroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    ArrayList<CardItem> cards;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, author;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            imageView = itemView.findViewById(R.id.imageView2);
        }
    }



    public MyAdapter(Context c, String s[], String s1[], int img[]){
        context = c;
        cards = new ArrayList<CardItem>();
        for(int i = 0; i < s.length; i++){
            cards.add(new CardItem(s[i], s1[i], img[i]));
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


}
