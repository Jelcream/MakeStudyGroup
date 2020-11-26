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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListsAdapter extends RecyclerView.Adapter<UserListsAdapter.UserListViewHolder>{
    ArrayList<ListInUser> listInUsers;
    Context context;


    public UserListsAdapter(Context context, ArrayList<ListInUser> listInUsers){
        this.listInUsers = listInUsers;
        this.context = context;
    }
    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_room,parent,false);
        return new UserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        holder.name.setText(listInUsers.get(position).getName());
        holder.img.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return listInUsers.size();
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        CircleImageView img;
        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title_view);
            img = itemView.findViewById(R.id.circleimage);
        }
    }
}
