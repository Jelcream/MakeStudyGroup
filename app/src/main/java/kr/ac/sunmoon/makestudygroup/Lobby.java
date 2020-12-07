package kr.ac.sunmoon.makestudygroup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Lobby extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<PostItem> cards;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewGroup viewGroup;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutmanager;
    String title[], author[];
    int img[];
    String s1[], s2[];

    public Lobby() {}
    // TODO: Rename and change types and number of parameters
    public static Lobby newInstance(String param1, String param2) {
        Lobby fragment = new Lobby();
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
        DatabaseReference databaseReference = firebaseDatabase.getReference("Post");
        cards = new ArrayList<PostItem>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("Lobby test", snapshot.getKey()+" "+cards.size());
                //cards.add(snapshot.getValue(PostItem.class));
                cards.add(snapshot.getValue(PostItem.class));

                mAdapter.notifyDataSetChanged();
                Log.e("card test",cards.get(cards.size()-1).getAuthor());
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
        // Inflate the layout for this
        viewGroup = (ViewGroup) inflater.inflate(R.layout.lobby, container, false);
        //cardView Test Start
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        mLayoutmanager = new LinearLayoutManager(viewGroup.getContext());
        recyclerView.setLayoutManager(mLayoutmanager);
        mAdapter = new LobbyAdapter(viewGroup.getContext(),cards);
        recyclerView.setAdapter(mAdapter);
        ((LobbyAdapter)mAdapter).setRecyListener(new OnRecyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PostItem post = ((LobbyAdapter) mAdapter).cards.get(position);
                Intent intent = new Intent(getContext(),ReadFormActivity.class);
                intent.putExtra("Post", post);
                startActivity(intent);
            }
        });
        //cardView Test End

        return viewGroup;
    }
}
