package com.bikram.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;


import com.bikram.practice.cardpageradapter.CardPagerAdapter1_9;
import com.google.android.material.slider.Slider;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class GlossaryActivitySerial extends AppCompatActivity {
    ViewPager viewPager;
    WormDotsIndicator wormDotsIndicator;
    Slider slider;
    int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary_serial);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Number 0-9");
        selectedPosition = getIntent().getIntExtra("letter", 0);
        setSupportActionBar(toolbar);
        wormDotsIndicator = (WormDotsIndicator) findViewById(R.id.worm_dots_indicator);
        viewPager = findViewById(R.id.viewPagerGlossaryLetter);
        slider = findViewById(R.id.slider);
        CardPagerAdapter1_9 mCardAdapter = new CardPagerAdapter1_9();
        for (int i = 0; i <= 9; i++) {
            int resourceId = getResources().getIdentifier("handshape_" + i, "drawable", getPackageName());
            mCardAdapter.addCardItem(new CardItem2(String.valueOf(i), resourceId));
        }
        slider.setValueFrom(0f);
        slider.setValueTo(10f);
        slider.setStepSize(1f);
        ShadowTransformer mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(1);

        viewPager.setCurrentItem(selectedPosition);
        slider.setValue(selectedPosition);
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