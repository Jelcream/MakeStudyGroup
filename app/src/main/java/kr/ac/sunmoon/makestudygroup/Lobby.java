package kr.ac.sunmoon.makestudygroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Lobby extends Fragment {
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
    String title[], author[];
    int img[];
    String s1[], s2[];

    public Lobby() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        viewGroup = (ViewGroup) inflater.inflate(R.layout.lobby, container, false);
        //cardView Test Start
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerview);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        mLayoutmanager = new LinearLayoutManager(viewGroup.getContext());
        recyclerView.setLayoutManager(mLayoutmanager);
        title = new String[10];
        author = new String[10];
        img = new int[10];

        for(int i = 0 ; i < 10 ; i++){
            title[i] = "title"+i;
        }
        for(int i = 0; i < 10; i++){
            author[i] = "author"+i;
        }
        for(int i = 0; i< 10; i++){
            img[i] = R.mipmap.ic_launcher;
        }

        mAdapter = new LobbyAdapter(viewGroup.getContext(), title, author, img);
        recyclerView.setAdapter(mAdapter);
        //cardView Test End

        return viewGroup;
    }
}
