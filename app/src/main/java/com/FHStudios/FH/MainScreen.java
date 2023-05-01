package com.FHStudios.FH;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    BottomNavigationView bottomNavigationView;
    Boolean flag=false;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        replaceFragment(new Frag1());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        mAuth=FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser()==null)
                {
                startActivity(new Intent(MainScreen.this,Signin.class));
                flag=true;
                }
            }
        };


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment;

                switch (item.getItemId()) {
                    case R.id.home_page:
                        selectedFragment = new Frag1();
                        break;
                    case R.id.statistics_page:
                        selectedFragment = new frag2();
                        break;
                    case R.id.profile_page:
                        selectedFragment = new frag3();
                        break;
                    default:
                        return false;
                }

                replaceFragment(selectedFragment);
                return true;
            }
        });

        if(getIntent().getStringExtra("news")!=null)flag=true;

    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
        if(flag){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.customdialog, viewGroup, false);
            builder.setView(dialogView);

            AlertDialog alertDialog = builder.create();
            Button btn=(Button)dialogView.findViewById(R.id.buttonOk);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });
            alertDialog.show();
        flag=false;}
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this,"if you want to sign out press the button",Toast.LENGTH_SHORT).show();
    }
}