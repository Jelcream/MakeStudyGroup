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

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    private ArrayList<Board> arrayList;
    private Context context;

    public BoardAdapter(ArrayList<Board> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BoardAdapter.BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        BoardViewHolder holder = new BoardViewHolder(view);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.BoardViewHolder holder, int position) {
        //Glide.with(holder.itemView).load(arrayList.get(position).getImg()).into(holder.img);
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_intro.setText(arrayList.get(position).getIntro());
        holder.tv_writer.setText(arrayList.get(position).getWriter_email());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class BoardViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_intro, tv_writer;
        ImageView img;
        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.img);
            this.tv_title = itemView.findViewById(R.id.title);
            this.tv_intro = itemView.findViewById(R.id.intro);
            this.tv_writer = itemView.findViewById(R.id.writer);
        }
    }
}
