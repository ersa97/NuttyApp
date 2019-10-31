package com.example.nuttyapp.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.nuttyapp.R;
import com.example.nuttyapp.ui.login.LoginActivity;
import com.example.nuttyapp.ui.signup.SignUpActivity;

public class WelcomeActivity extends AppCompatActivity {

    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent (WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },2000);
    }

}
