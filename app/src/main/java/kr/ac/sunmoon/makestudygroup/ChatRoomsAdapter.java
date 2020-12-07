package kr.ac.sunmoon.makestudygroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomsViewHolder> {
    ArrayList<GroupItem> roomitem;
    Context context;
    private OnRecyItemClickListener itemClickListener;

    public void setOnRoomsItemClickListener(OnRecyItemClickListener listener){
        itemClickListener = listener;
    }

    public ChatRoomsAdapter(Context c, ArrayList<GroupItem> roomitem ){
        this.roomitem = roomitem;
        this.context = c;
    }
    @NonNull
    @Override
    public ChatRoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_room,parent,false);
        return new ChatRoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomsViewHolder holder, int position) {
        holder.title.setText(roomitem.get(position).getTitle());
        Glide.with(context).load(roomitem.get(position).getImage()).error(R.drawable.logo).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return roomitem.size();
    }

    public class ChatRoomsViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView title;

        public ChatRoomsViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(itemClickListener != null){
                            itemClickListener.onItemClick(view, pos);
                        }
                    }
                }
            });
            img = itemView.findViewById(R.id.circleimage);
            title = itemView.findViewById(R.id.title_view);

        }
    }
}
