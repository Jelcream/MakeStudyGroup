package kr.ac.sunmoon.makestudygroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{
    ArrayList<Chat> chats;
    Context context;
    public ChatAdapter(Context context, ArrayList<Chat> chats){
        this.chats = chats;
        this.context = context;
    }
    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat,parent,false);
        return new ChatAdapter.ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        holder.contents.setText(chats.get(position).getContent());
        holder.date.setText(chats.get(position).getDate());
        holder.name.setText(chats.get(position).getAuthor());
        holder.img.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView name, contents, date;
        CircleImageView img;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.editor);
            contents = (TextView) itemView.findViewById(R.id.chatting_content);
            date = (TextView) itemView.findViewById(R.id.chatdate);
            img = (CircleImageView) itemView.findViewById(R.id.profile);
        }
    }

}
