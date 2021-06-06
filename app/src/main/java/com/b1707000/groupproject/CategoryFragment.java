package com.b1707000.groupproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private RecyclerView categoryView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_category, container, false);
        
        Chip romance = root.findViewById(R.id.romance);
        Chip action = root.findViewById(R.id.action);
        Chip romcom = root.findViewById(R.id.romcom);
        Chip harem = root.findViewById(R.id.harem);
        categoryView = root.findViewById(R.id.categoryView);
        mDatabase = FirebaseDatabase.getInstance("https://groupproject-3f4f0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        romance.setOnClickListener(this);
        action.setOnClickListener(this);
        romcom.setOnClickListener(this);
        harem.setOnClickListener(this);
        romance.setChecked(true);
        loadData("Tình cảm",categoryView);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.action:
                Log.d("Chip","Action");
                loadData("Hành động",categoryView);
                break;
            case R.id.romance:
                Log.d("Chip","Romance");
                loadData("Tình cảm",categoryView);
                break;
            case R.id.romcom:
                Log.d("Chip","Romcom");
                loadData("Romcom",categoryView);
                break;
            case R.id.harem:
                Log.d("Chip","Harem");
                loadData("Harem",categoryView);
        }
        
    }
    private void loadData(String category, RecyclerView categoryView){
        final MovieAdapter[] movieAdapter = {null};

        Query query = mDatabase.child("movies/").orderByChild("category").equalTo(category);
        ArrayList<Movie> movies = new ArrayList<>();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable String previousChildName) {
                movies.add(snapshot.getValue(Movie.class));
                if (movieAdapter[0] == null){
                    movieAdapter[0] = new MovieAdapter(movies,getContext());
                    categoryView.setAdapter(movieAdapter[0]);
                }
                movieAdapter[0].notifyItemChanged(movies.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull  DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        categoryView.setLayoutManager(new GridLayoutManager(getContext(),2));
    }
    
}