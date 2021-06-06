package com.b1707000.groupproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserLoginFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserLoginFrag extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth mAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserLoginFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserLoginFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static UserLoginFrag newInstance(String param1, String param2) {
        UserLoginFrag fragment = new UserLoginFrag();
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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_user_login, container, false);
        Button btnLike = root.findViewById(R.id.favourite);
        Button btnLogout = root.findViewById(R.id.logout);
        TextView user = root.findViewById(R.id.user);
        btnLike.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();
        user.setText(email);
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout){

            mAuth.signOut();
            SharedPreferences ref = getContext().getSharedPreferences("ref", Context.MODE_PRIVATE);
            ref.edit().clear().apply();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, new HomeFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            BottomNavigationView btm = getActivity().findViewById(R.id.btNav);
            btm.setSelectedItemId(R.id.nav_home);

        }
        if (v.getId() == R.id.favourite){
            Intent intent = new Intent(getContext(),FavouriteActivity.class);
            startActivity(intent);
        }
    }
}