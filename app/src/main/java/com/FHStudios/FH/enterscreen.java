package com.FHStudios.FH;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.FHStudios.FH.R;

public class enterscreen extends AppCompatActivity {
Handler han; static int time=1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in= new Intent(enterscreen.this, MainScreen.class);
                startActivity(in);
                finish();
            }
        },time);
    }
}