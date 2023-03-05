package com.bikram.practice.cardview;

import static com.bikram.practice.CardAdapter.MAX_ELEVATION_FACTOR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.bikram.practice.CardItem;
import com.bikram.practice.R;
import com.bikram.practice.ShadowTransformer;
import com.bikram.practice.cardpageradapter.CardPagerAdapter;
import com.google.android.material.slider.Slider;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class cardviewA2H extends AppCompatActivity {
    ViewPager viewPager;
    WormDotsIndicator wormDotsIndicator;
    Slider slider;
    private float mBaseElevation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview_a2_h);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Letters A-H");
        setSupportActionBar(toolbar);
        wormDotsIndicator = (WormDotsIndicator) findViewById(R.id.worm_dots_indicator);
        viewPager = findViewById(R.id.viewPager);
        slider = findViewById(R.id.slider);
        CardPagerAdapter mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem("A", R.raw.a));
        mCardAdapter.addCardItem(new CardItem("B", R.raw.b));
        mCardAdapter.addCardItem(new CardItem("C", R.raw.c));
        mCardAdapter.addCardItem(new CardItem("D", R.raw.d));
        mCardAdapter.addCardItem(new CardItem("E", R.raw.e));
        mCardAdapter.addCardItem(new CardItem("F", R.raw.f));
        mCardAdapter.addCardItem(new CardItem("G", R.raw.g));
        mCardAdapter.addCardItem(new CardItem("H", R.raw.h));
        mCardAdapter.setOnInstantiateItem(new CardPagerAdapter.onInstantiateItemListener() {
            @Override
            public Object onInstantiateItem(ViewGroup container, int position) {
                if (position == CardPagerAdapter.mData.size()) {
                    View view = LayoutInflater.from(container.getContext())
                            .inflate(R.layout.custom_page_end, container, false);

                    container.addView(view);
                    Button button = view.findViewById(R.id.next);
                    TextView tt = view.findViewById(R.id.animTextView);
                    tt.startAnimation(AnimationUtils.loadAnimation(view.getContext(), android.R.anim.fade_in));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(view.getContext(), cardviewI2P.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            view.getContext().startActivity(intent);
                            ((Activity) view.getContext()).finish();

                        }
                    });

                    return view;
                } else {
                    View view = LayoutInflater.from(container.getContext())
                            .inflate(R.layout.cardviewadapter, container, false);
                    container.addView(view);
                    CardPagerAdapter.bind(CardPagerAdapter.mData.get(position), view);
                    CardView cardView = (CardView) view.findViewById(R.id.cardView);
                    if (mBaseElevation == 0) {
                        mBaseElevation = cardView.getCardElevation();
                    }

                    cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
                    CardPagerAdapter.mViews.set(position, cardView);
                    return view;
                }
            }
        });


        slider.setValueFrom(0f);
        slider.setValueTo(8f);
        slider.setStepSize(1f);

        ShadowTransformer mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(1);


        wormDotsIndicator.attachTo(viewPager);
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                viewPager.setCurrentItem((int) value, true);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                slider.setValue(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}