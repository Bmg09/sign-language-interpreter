package com.bikram.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new LessonFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.practice:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new PracticeFragment()).commit();
                        break;
                    case R.id.lesson:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new LessonFragment()).commit();
                        break;
                    case R.id.Account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AccountFragment()).commit();
                        break;
                    case R.id.Glossary:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new GlossaryFragment()).commit();
                        break;
                }
                return true;
            }
        });

    }
}
