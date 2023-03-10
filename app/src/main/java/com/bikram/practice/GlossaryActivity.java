package com.bikram.practice;

import static com.bikram.practice.CardAdapter.MAX_ELEVATION_FACTOR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bikram.practice.cardpageradapter.CardPagerAdapter;
import com.bikram.practice.cardview.cardviewI2P;
import com.google.android.material.slider.Slider;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.List;
import java.util.Locale;

public class GlossaryActivity extends AppCompatActivity {
    ViewPager viewPager;
    WormDotsIndicator wormDotsIndicator;
    Slider slider;
    ImageView imageView;
    int selectedPosition;
    private float mBaseElevation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Letters A-Z");
        setSupportActionBar(toolbar);
        selectedPosition = getIntent().getIntExtra("letter", 0);
        Toast.makeText(this, String.valueOf(selectedPosition), Toast.LENGTH_SHORT).show();
        wormDotsIndicator = (WormDotsIndicator) findViewById(R.id.worm_dots_indicator);
        viewPager = findViewById(R.id.viewPagerGlossaryLetter);
        slider = findViewById(R.id.slider);
        imageView = findViewById(R.id.reset);
        CardPagerAdapter mCardAdapter = new CardPagerAdapter();


        mCardAdapter.addCardItem(new CardItem("A", R.raw.a));
        mCardAdapter.addCardItem(new CardItem("B", R.raw.b));
        mCardAdapter.addCardItem(new CardItem("C", R.raw.c));

        for (char c = 'A'; c <= 'Z'; c++) {
            int resourceId = getResources().getIdentifier(String.valueOf(c).toLowerCase(Locale.ROOT), "raw", getPackageName());
            mCardAdapter.addCardItem(new CardItem(String.valueOf(c),resourceId));
        }
        ShadowTransformer mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        mCardAdapter.setOnInstantiateItem(new CardPagerAdapter.onInstantiateItemListener() {
            @Override
            public Object onInstantiateItem(ViewGroup viewGroup, int position) {
                if (position == CardPagerAdapter.mData.size()) {
                    View view = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.custom_page_end, viewGroup, false);

                    viewGroup.addView(view);
                    Button button = view.findViewById(R.id.next);
                    TextView tt = view.findViewById(R.id.animTextView);
                    tt.startAnimation(AnimationUtils.loadAnimation(view.getContext(), android.R.anim.fade_in));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(view.getContext(), GlossaryActivityNumbers.class) ;
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            view.getContext().startActivity(intent);
                            ((Activity) view.getContext()).finish();

                        }
                    });
                    return view;
                } else {
                    View view = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.cardviewadapter, viewGroup, false);
                    viewGroup.addView(view);
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
        slider.setValueTo(29f);
        slider.setStepSize(1f);

        viewPager.setAdapter(mCardAdapter);
        viewPager.setCurrentItem(selectedPosition);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        wormDotsIndicator.attachTo(viewPager);
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
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