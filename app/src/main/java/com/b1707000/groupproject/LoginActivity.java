package com.b1707000.groupproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashSet;
import java.util.Set;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtEmail;
    TextView txtPassword;
    private FirebaseAuth mAuth;
    SharedPreferences ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView btnRegister = findViewById(R.id.btnTextRegister);
        Button loginBtn = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        mAuth = FirebaseAuth.getInstance();
        ref = getSharedPreferences("ref",MODE_PRIVATE);
        loginBtn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnTextRegister){
           Intent intent = getIntent();
           if (intent.getStringExtra("from").equals("Main")){
               intent = new Intent(getBaseContext(),RegisterActivity.class);
               intent.putExtra("from","Login");
               startActivity(intent);
           }
           else{
               finishAfterTransition();
           }


        }
        if (v.getId() == R.id.btnLogin){
            String email = txtEmail.getText().toString();
            String pass = txtPassword.getText().toString();
            signIn(email,pass);
            Log.d("Email",email);
            Log.d("Password",pass);
        }
    }

    private void signIn(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Created", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        ref.edit().putString("uid",user.getUid()).apply();
                        likeList(user.getUid());
                        finishAfterTransition();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Error", "createUserWithEmail:failure", task.getException());
                        txtEmail.setError("Sai tài khẩu hoặc mật khẩu");
                    }
                });
    }
    private void likeList(String uid){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://groupproject-3f4f0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        Query likeQuery = mDatabase.child("likes/").orderByKey().equalTo(uid);
        likeQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

                Like like = snapshot.getValue(Like.class);
                Set<String> filmIDs = new HashSet<String>();
                filmIDs.addAll(like.getFilmIDs());
                ref.edit().putStringSet("filmIDs",filmIDs).apply();

            }

            @Override
            public void onChildChanged(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}