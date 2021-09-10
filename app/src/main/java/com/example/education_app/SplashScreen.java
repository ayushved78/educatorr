package com.example.education_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportActionBar
//        startActivity(new Intent(this, relog.class));
        startActivity(new Intent(this, reg.class));
    }
}
