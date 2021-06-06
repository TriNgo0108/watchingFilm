package com.b1707000.groupproject;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText txtEmail;
    EditText txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView btnLogin = findViewById(R.id.btnTextLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister){
            Intent intent = getIntent();
            if (intent.getStringExtra("from").equals("Main")){
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                Log.d("Creating","Waiting");
                if (((email.equals("") || email == null)) || (password.equals("") ||(password == null))) {
                    txtEmail.setError("Email không được để trống");
                    txtPassword.setError("Password không được để trống");
                }
                else {
                    createUser(email,password);
                }
            }
        }
        if (v.getId() == R.id.btnTextLogin){
            Intent intent = getIntent();
            if (intent.getStringExtra("from").equals("Main")){
                intent = new Intent(this,LoginActivity.class);
                intent.putExtra("from","Register");
                startActivity(intent);
            }
            else{
                finishAfterTransition();
            }
        }
    }
    private void createUser(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Created", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        DatabaseReference mData = FirebaseDatabase.getInstance("https://groupproject-3f4f0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                        Intent intent = new Intent(this,LoginActivity.class);
                        intent.putExtra("from","Register");
                        startActivity(intent);
                        finishAfterTransition();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Error", "createUserWithEmail:failure", task.getException());
                        txtEmail.setError("Có lỗi xảy ra vui lòng thử lại");
                    }
                });
    }

}