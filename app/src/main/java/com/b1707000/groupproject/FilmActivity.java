package com.b1707000.groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FilmActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ImageView filmImage;
    TextView filmName;
    TextView publishDate;
    TextView category;
    TextView description;
    TextView amount;
    CheckBox like;
    private DatabaseReference mDataBase;
    private SharedPreferences ref;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        filmImage = findViewById(R.id.video);
        filmName = findViewById(R.id.filmName);
        publishDate = findViewById(R.id.publishDate);
        category = findViewById(R.id.category);
        description = findViewById(R.id.description);
        amount = findViewById(R.id.episode);
        like = findViewById(R.id.like);
        mDataBase = FirebaseDatabase.getInstance("https://groupproject-3f4f0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        ref = getSharedPreferences("ref",MODE_PRIVATE);
        loadData();
        like.setOnCheckedChangeListener(this);

    }
    private void loadData(){
        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("imgURL")).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_error_24).into(filmImage);
        filmName.setText(intent.getStringExtra("filmName"));
        publishDate.setText(intent.getStringExtra("publishDate"));
        category.setText(intent.getStringExtra("category"));
        description.setText(intent.getStringExtra("description"));
        amount.setText(String.format("%s tập", String.valueOf(intent.getIntExtra("amount", 0))));
        id = intent.getStringExtra("id");
        Log.d("ID",id);

        Set<String> likes = ref.getStringSet("filmIDs",null);
        if (likes != null){
            if (likes.contains(id)){
                like.setChecked(true);
            }
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String uid = ref.getString("uid",null);
        if (uid == null){
            Toast.makeText(this, "Bạn cần phải đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
            like.setChecked(false);
        }
        else{
            if (isChecked) {
                Log.d("Checked", "true");
                Set<String> filmIDs = ref.getStringSet("filmIDs", null);
                if (filmIDs == null) filmIDs = new HashSet<>();
                filmIDs.add(id);
                Log.d("Test",filmIDs.toString());
                ref.edit().putStringSet("filmIDs",filmIDs).apply();
                ArrayList<String> finalFilmIDs = new ArrayList<>(filmIDs);
                Like like = new Like(finalFilmIDs);
                mDataBase.child("likes/"+uid).setValue(like);
            }
            else {
                Log.d("Checked","false");
                Set<String> filmIDs = ref.getStringSet("filmIDs", null);
                if (filmIDs == null) filmIDs = new HashSet<>();
                filmIDs.remove(id);
                Log.d("Test",filmIDs.toString());
                ref.edit().putStringSet("filmIDs",filmIDs).apply();
                ArrayList<String> finalFilmIDs = new ArrayList<>(filmIDs);
                Like like = new Like(finalFilmIDs);
                mDataBase.child("likes/"+uid).setValue(like);
            }
        }


    }

}