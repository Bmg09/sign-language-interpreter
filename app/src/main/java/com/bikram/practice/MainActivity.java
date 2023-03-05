package com.bikram.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.bikram.practice.fragments.AccountFragment;
import com.bikram.practice.fragments.GlossaryFragment;
import com.bikram.practice.fragments.LessonFragment;
import com.bikram.practice.fragments.PracticeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successfull";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
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
