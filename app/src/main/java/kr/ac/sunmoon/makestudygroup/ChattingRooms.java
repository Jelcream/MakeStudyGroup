package kr.ac.sunmoon.makestudygroup;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChattingRooms#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChattingRooms extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewGroup viewGroup;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutmanager;
    private ArrayList<GroupItem> groupItems;

    public ChattingRooms() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChattingRooms.
     */
    // TODO: Rename and change types and number of parameters
    public static ChattingRooms newInstance(String param1, String param2) {
        ChattingRooms fragment = new ChattingRooms();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference groupDB = firebaseDatabase.getReference("Group");
        DatabaseReference userRoomDB = firebaseDatabase.getReference("UserRoom");
        final User user = MyUser.getInstance().getUser();
        final HashMap<Object, String> map = new HashMap<>();
        groupItems = new ArrayList<GroupItem>();
        userRoomDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().equals(user.getUid())){
                    for(DataSnapshot s : snapshot.getChildren()){
                        map.put(s.getValue(),s.getKey());
                        Log.e("snapshot chatting", s.getKey());
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        groupDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String title = null, uid = null;
                if(map.containsKey(snapshot.getKey())){
                    Log.e("groupDB map test", snapshot.getKey());
                    for(DataSnapshot s : snapshot.getChildren()){
                        Log.e("snapshot children", s.getKey());
                        if(s.getKey().equals("GroupID")){
                            uid = (String)s.getValue();
                        }else if(s.getKey().equals("GroupTitle")){
                            title = (String)s.getValue();
                        }
                    }
                    if(title != null && uid != null) {
                        groupItems.add(new GroupItem(title, uid));
                        Log.e("roomitem", groupItems.get(groupItems.size()-1).getTitle());
                        mAdapter.notifyDataSetChanged();
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.chatting_rooms, container, false);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyRooms);

        recyclerView.setHasFixedSize(true);
        mLayoutmanager = new LinearLayoutManager(viewGroup.getContext());

        recyclerView.setLayoutManager(mLayoutmanager);

//        String[] title = new String[10];
//        int[] img = new int[10];
//
//        for(int i = 0 ; i < 10; i++){
//            img[i] = R.mipmap.ic_launcher;
//            title[i] = Integer.toString(i);
//        }
        mAdapter = new ChatRoomsAdapter(viewGroup.getContext(), groupItems);
        recyclerView.setAdapter(mAdapter);
        ((ChatRoomsAdapter)mAdapter).setOnRoomsItemClickListener(new OnRecyItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), ChattingRoom.class);
                intent.putExtra("Uid", groupItems.get(position).getId());
                startActivity(intent);
            }
        });
        return  viewGroup;
    }
}