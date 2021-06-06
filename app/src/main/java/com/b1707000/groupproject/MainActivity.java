package com.b1707000.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView btmNav;
    SharedPreferences ref;
    int exit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btmNav = findViewById(R.id.btNav);
        btmNav.setOnNavigationItemSelectedListener(this);
        ref = getSharedPreferences("ref",MODE_PRIVATE);
        loadFragment(new HomeFragment());

    }

    @Override
    protected void onResume() {
        super.onResume();
        String uid = ref.getString("uid",null);
        if (uid != null){
            loadFragment(new HomeFragment());
            btmNav.setSelectedItemId(R.id.nav_home);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit++;
            loadFragment(new HomeFragment());
            btmNav.setSelectedItemId(R.id.nav_home);
            Toast.makeText(this, "Nhấn back hêm một nữa để thoát", Toast.LENGTH_SHORT).show();
            if (exit > 1){
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()){
            case R.id.nav_home:
                fragment = new HomeFragment();
                loadFragment(fragment);
                return  true;
            case  R.id.nav_category:
                fragment = new CategoryFragment();
                loadFragment(fragment);
                return  true;
            case R.id.nav_user:
                SharedPreferences ref = getSharedPreferences("ref",MODE_PRIVATE);
                String uid = ref.getString("uid",null);
                if (uid != null){
                    fragment = new UserLoginFrag();
                }
                else{
                    fragment = new UserFragment();
                }
                loadFragment(fragment);

                return true;
        }
        return false;
    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}