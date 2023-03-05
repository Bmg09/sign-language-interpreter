package com.bikram.practice.cardpageradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bikram.practice.CardAdapter;
import com.bikram.practice.CardItem;
import com.bikram.practice.CardItem2;
import com.bikram.practice.R;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter1_9 extends PagerAdapter implements CardAdapter {
    private List<CardView> mViews;
    private List<CardItem2> mData;
    private float mBaseElevation;

    public CardPagerAdapter1_9() {
        mData = new ArrayList<CardItem2>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem2 item) {
        mViews.add(null);
        mData.add(item);
    }

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
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.cardviewsingle1_9, container, false);
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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem2 item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.numlet);
        ImageView imageView = (ImageView) view.findViewById(R.id.image1_9);
        titleTextView.setText(item.getLetter());
       imageView.setImageResource(item.getImageId());
    }
}
