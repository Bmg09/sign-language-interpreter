package com.bikram.practice;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    public static ExoPlayer player;
    public static StyledPlayerView styledPlayerView;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }


    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        StyledPlayerView styledPlayerView = view.findViewById(R.id.videoViewA2H);
        ExoPlayer player = (ExoPlayer) styledPlayerView.getPlayer();

        // Release the player to free up resources
        if (player != null) {
            player.release();
        }
        container.removeView((View) object);
        mViews.set(position, null);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.cardviewadapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.letterA2H);
        titleTextView.setText(item.getLetter());
        player = new ExoPlayer.Builder(view.getContext()).build();
        styledPlayerView = view.findViewById(R.id.videoViewA2H);
        styledPlayerView.setPlayer(player);
        Uri player_uri = Uri.parse("android.resource://com.bikram.practice/" + item.getVidResId());
        MediaItem mediaItem = MediaItem.fromUri(player_uri);

        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
        player.setRepeatMode(player.REPEAT_MODE_ONE);
    }

    // https://github.com/google/ExoPlayer/issues/7750
    // workaround for black screen issues removing each view and reinitializing again
    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
        int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = container.getChildAt(i);
            if (view != null) {
                StyledPlayerView styledPlayerView = view.findViewById(R.id.videoViewA2H);
                ExoPlayer player = (ExoPlayer) styledPlayerView.getPlayer();
                if (player != null){
                    // This is the last item, so pause the player
                    // This is not the last item, so resume the player
                    player.setPlayWhenReady(i != container.indexOfChild(container.getChildAt(container.getChildCount()-2 )));
                }
            }
        }
    }
}