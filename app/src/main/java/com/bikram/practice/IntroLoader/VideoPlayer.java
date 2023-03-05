package com.bikram.practice.IntroLoader;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bikram.practice.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class VideoPlayer extends AppCompatActivity {
    ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        player = new ExoPlayer.Builder(this).build();
        StyledPlayerView styledPlayerView = findViewById(R.id.videoView);
        styledPlayerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/ty-projectsli.appspot.com/o/intro_for_mobile_final-hevc.mp4?alt=media&token=1b4ad48d-2865-4c00-b16c-a189d440402a");
//        Uri player_uri = Uri.parse("android.resource://com.bikram.practice/" + R.raw.a);
//        MediaItem mediaItem = MediaItem.fromUri(player_uri);

        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
//        player.setRepeatMode();
    }

    @Override
    protected void onPause() {
//        Toast.makeText(this, "hh", Toast.LENGTH_SHORT).show();
        player.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        player.release();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
//        Toast.makeText(this, "resume called", Toast.LENGTH_SHORT).show();
        player.play();
        super.onResume();
    }
}