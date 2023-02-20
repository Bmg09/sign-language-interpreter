package com.bikram.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.slider.Slider;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class cardviewA2H extends AppCompatActivity {
    ViewPager viewPager;
    WormDotsIndicator wormDotsIndicator;
    Slider slider;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    int maxOffset;
    int currentOffset;
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
        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem("A", R.raw.a));
        mCardAdapter.addCardItem(new CardItem("B", R.raw.b));
        mCardAdapter.addCardItem(new CardItem("C", R.raw.c));
//        mCardAdapter.addCardItem(new CardItem("B", R.raw.watchvideo));
//        mCardAdapter.addCardItem(new CardItem("C", R.raw.camera));

        mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        wormDotsIndicator.attachTo(viewPager);
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
//                if(viewPager.isFakeDragging()){
//                    int offset = (int) ((maxOffset / 100.0) * value);
//
//                    int dragby = -1 * (offset - currentOffset);
//                    Log.d("DRAGBY", "" + value);
//                    viewPager.fakeDragBy(dragby);
//
//                    currentOffset = offset;
//                }
                viewPager.setCurrentItem((int) value);
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