package com.b1707000.groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Set;

public class FavouriteActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TextView emptyList;
    RecyclerView favouriteView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        mDatabase = FirebaseDatabase.getInstance("https://groupproject-3f4f0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        emptyList = findViewById(R.id.emptyList);
        favouriteView = findViewById(R.id.favouriteView);
        SharedPreferences ref = getSharedPreferences("ref",MODE_PRIVATE);
        Set<String> favourSet = ref.getStringSet("filmIDs",null);
        if (favourSet == null){
            emptyList.setVisibility(View.VISIBLE);
        }
        else {
            emptyList.setVisibility(View.INVISIBLE);
            final MovieAdapter[] movieAdapter = {null};
            Query query = mDatabase.child("movies/").orderByChild("id");
            ArrayList<Movie> movies = new ArrayList<>();
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Movie movie = snapshot.getValue(Movie.class);
                    if(favourSet.contains(movie.getId())){
                        movies.add(snapshot.getValue(Movie.class));
                        favouriteView.setAdapter(movieAdapter[0]);
                    }
                    if (movieAdapter[0] == null)
                        movieAdapter[0] = new MovieAdapter(movies, getBaseContext());
                    else movieAdapter[0].notifyItemChanged(movies.size() - 1);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
           favouriteView.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
        }
    }
    private void loadData(String child, RecyclerView recyclerView){
        final MovieAdapter[] movieAdapter = {null};
        Query query = mDatabase.child(child+"/").orderByChild("id");
        ArrayList<Movie> movies = new ArrayList<>();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                movies.add(snapshot.getValue(Movie.class));
                if (movieAdapter[0] == null){
                    movieAdapter[0] = new MovieAdapter(movies,getBaseContext());
                    recyclerView.setAdapter(movieAdapter[0]);
                }
                movieAdapter[0].notifyItemChanged(movies.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}