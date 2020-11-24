package kr.ac.sunmoon.makestudygroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomsViewHolder> {
    ArrayList<RoomItem> roomitem;
    Context context;


    public ChatRoomsAdapter(Context c, String[] title, int[] img ){
        roomitem = new ArrayList<RoomItem>();
        this.context = c;
        for(int i = 0; i <title.length; i++){
            roomitem.add(new RoomItem(title[i], img[i]));
        }
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
        holder.img.setImageResource(roomitem.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return roomitem.size();
    }

    public static class ChatRoomsViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView title;

        public ChatRoomsViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.circleimage);
            title = itemView.findViewById(R.id.title_view);

        }
    }
}
