package com.bikram.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class cardviewA2H extends AppCompatActivity {
    ViewPager viewPager;
    WormDotsIndicator wormDotsIndicator;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

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
    }
}