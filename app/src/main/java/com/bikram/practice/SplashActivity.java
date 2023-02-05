package com.bikram.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextSwitcher;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    TextSwitcher textSwitcher;
    int currentIndex;
    Handler handler;
    FirebaseUser auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        textSwitcher = findViewById(R.id.textSwitcher);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String[] textToShow = {"Sign Language Interpreter"};
                textSwitcher.setInAnimation(SplashActivity.this, android.R.anim.slide_in_left);
                currentIndex++;
                if (currentIndex == textToShow.length) {
                    currentIndex = 0;
                }
                textSwitcher.setText(textToShow[currentIndex]);
            }
        },500);
        Handler tonewactivity = new Handler();
        tonewactivity.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(auth != null && auth.isEmailVerified() && isSignedIn()){

                 startActivity(new Intent(SplashActivity.this,MainActivity.class));
                 finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this,SignupActivity.class));
                    finish();
                }
            }
        },2000);
    }
    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(SplashActivity.this) != null;
    }
}