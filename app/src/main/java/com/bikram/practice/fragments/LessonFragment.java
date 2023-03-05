package com.bikram.practice.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bikram.practice.IntroLoader.VideoPlayer;
import com.bikram.practice.R;
import com.bikram.practice.cardpageradapter.CardPagerAdapter1_9;
import com.bikram.practice.cardview.cardview0_9;
import com.bikram.practice.cardview.cardview1_9;
import com.bikram.practice.cardview.cardviewA2H;
import com.bikram.practice.cardview.cardviewI2P;
import com.bikram.practice.cardview.cardviewQ2Z;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;

public class LessonFragment extends Fragment {
    CardView letterA2H,letterI2P,letterQ2Z,letter1_9;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LessonFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LessonFragment newInstance(String param1, String param2) {
        LessonFragment fragment = new LessonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Lessons");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                int cx = (view.getLeft() + view.getRight()) / 2;
                int cy = (view.getTop() + view.getBottom()) / 2;
                int finalRadius = Math.max(view.getWidth(), view.getHeight());

                Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
                int color = Color.parseColor("#FFECB3");
                view.setBackgroundColor(color);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
//                animateButtonsIn();
                    }
                });
                anim.start();
            }
        }, 0);
        letterA2H = view.findViewById(R.id.cardView);
        letterA2H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(getContext(), cardviewA2H.class));
            }
        });
        letterI2P = view.findViewById(R.id.cardView2);
        letterI2P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(getContext(), cardviewI2P.class));
            }
        });
        letterQ2Z = view.findViewById(R.id.cardView3);
        letterQ2Z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(getContext(), cardviewQ2Z.class));
            }
        });
        letter1_9 = view.findViewById(R.id.cardView4);
        letter1_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(getContext(), cardview1_9.class));
            }
        });
        CardView letter0_9 = view.findViewById(R.id.cardView5);
        letter0_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(getContext(), cardview0_9.class));
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.lesson_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.intro:
                startActivity(new Intent (getActivity(), VideoPlayer.class));
                Toast.makeText(getActivity(), "Intro", Toast.LENGTH_SHORT).show();
                break;
            case R.id.info:
                FancyAlertDialog.Builder.with(getContext()).setTitle("About Lesson Page")
                    .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                    .setMessage("We have provided Two Categories as lessons for user to practice the signs Letters and Numbers")
                    .setNegativeBtnText("Cancel")
                    .setPositiveBtnBackground(Color.parseColor("#FF4081"))  // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
                    .setPositiveBtnText("Ok")
                    .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorvalue)
                    .setAnimation(Animation.SLIDE)
                    .isCancellable(true)
                    .setIcon(R.drawable.lesson, View.VISIBLE)
                    .onPositiveClicked(dialog -> Toast.makeText(getContext(), "Enjoy!", Toast.LENGTH_SHORT).show())
                    .onNegativeClicked(dialog -> Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show())
                    .build()
                    .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}