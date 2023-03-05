package com.bikram.practice.cardpageradapter;


import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bikram.practice.CardAdapter;
import com.bikram.practice.CardItem;
import com.bikram.practice.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.util.ArrayList;
import java.util.List;
//Inspired by https://github.com/rubensousa/ViewPagerCards
public class CardPagerAdapter extends PagerAdapter implements CardAdapter {
    public static List<CardView> mViews;
    public static List<CardItem> mData;
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
        if (mViews.size() > mData.size()) {
            mViews.remove(mViews.size() - 1);
        }
    }


    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        if (position >= 0 && position < mViews.size()) {
            return mViews.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return mData.size() + 1;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (position < 0 || position >= mViews.size()) {
            return; // index out of bounds, do nothing
        }
        View view = (View) object;
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        View playerView = view.findViewById(R.id.videoViewA2H);

        if (playerView instanceof StyledPlayerView) {
            ExoPlayer player = (ExoPlayer) ((StyledPlayerView) playerView).getPlayer();

            // Release the player to free up resources
            if (player != null) {
                player.release();
            }
        }

        container.removeView(view);
        mViews.set(position, null);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object v = null;
        if(listener!=null){
            v = listener.onInstantiateItem(container,position);
        }
        assert v != null;
        return v;
    }

    public static void bind(CardItem item, View view) {
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
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int j = 0; j < viewGroup.getChildCount(); j++) {
                    View childView = viewGroup.getChildAt(j);
                    if (childView instanceof StyledPlayerView) {
                        StyledPlayerView styledPlayerView = (StyledPlayerView) childView;
                        ExoPlayer player = (ExoPlayer) styledPlayerView.getPlayer();
                        if (player != null) {
                            player.release();
                        }
                    }
                }
            }
        }
    }
    public interface onInstantiateItemListener{
        Object onInstantiateItem(ViewGroup viewGroup,int position);
    }
    private onInstantiateItemListener listener;
    public void setOnInstantiateItem(onInstantiateItemListener onInstantiateItemListener){
        listener = onInstantiateItemListener;
    }
}