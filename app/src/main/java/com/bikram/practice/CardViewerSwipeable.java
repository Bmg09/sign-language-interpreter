package com.bikram.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class CardViewerSwipeable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_viewer_swipeable);
        Toast.makeText(this, String.valueOf(getIntent().getStringExtra("t")), Toast.LENGTH_SHORT).show();
    }
}