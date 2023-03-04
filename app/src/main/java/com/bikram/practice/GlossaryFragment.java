package com.bikram.practice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GlossaryFragment extends Fragment {

    RecyclerView rvletter, rvnumber, rvserial;

    public GlossaryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_glossary, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Glossary");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);


        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                int cx = (view.getLeft() + view.getRight()) / 2;
                int cy = (view.getTop() + view.getBottom()) / 2;
                int finalRadius = Math.max(view.getWidth(), view.getHeight());

                Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
                int color = Color.parseColor("#FFE8F5E9");
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


        List<String> letters = new ArrayList<>();
        {
            letters.add("A");
            letters.add("B");
            letters.add("C");
            letters.add("D");
            letters.add("E");
            letters.add("F");
            letters.add("G");
            letters.add("H");
            letters.add("I");
            letters.add("J");
            letters.add("K");
            letters.add("L");
            letters.add("M");
            letters.add("N");
            letters.add("O");
            letters.add("P");
            letters.add("Q");
            letters.add("R");
            letters.add("S");
            letters.add("T");
            letters.add("U");
            letters.add("V");
            letters.add("W");
            letters.add("X");
            letters.add("Y");
            letters.add("Z");
        }
        List<String> numbers = new ArrayList<>();
        {
            numbers.add("1");
            numbers.add("2");
            numbers.add("3");
            numbers.add("4");
            numbers.add("5");
            numbers.add("6");
            numbers.add("7");
            numbers.add("8");
            numbers.add("9");
        }
        List<String> serial_number = new ArrayList<>();
        {
            serial_number.add("0");
            serial_number.add("1");
            serial_number.add("2");
            serial_number.add("3");
            serial_number.add("4");
            serial_number.add("5");
            serial_number.add("6");
            serial_number.add("7");
            serial_number.add("8");
            serial_number.add("9");
        }

        int resId = R.anim.grid_layout_animation_from_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);

        rvletter = view.findViewById(R.id.letter_recycler);
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), letters);
        rvletter.setLayoutManager(new GridLayoutManager(getContext(), 5));
        rvletter.setLayoutAnimation(animation);
        rvletter.setNestedScrollingEnabled(false);
        rvletter.setAdapter(adapter);
        


        rvnumber = view.findViewById(R.id.number_recycler);
        RecyclerAdapter adapterNum = new RecyclerAdapter(getContext(), numbers);
        rvnumber.setLayoutManager(new GridLayoutManager(getContext(), 5));
        rvnumber.setLayoutAnimation(animation);
        rvnumber.setNestedScrollingEnabled(false);
        rvnumber.setAdapter(adapterNum);

        rvserial = view.findViewById(R.id.serial_number_recycler);
        RecyclerAdapter adapterSerial = new RecyclerAdapter(getContext(), serial_number);
        rvserial.setLayoutManager(new GridLayoutManager(getContext(), 5));
        rvserial.setNestedScrollingEnabled(false);
        rvserial.setAdapter(adapterSerial);

        return view;
    }
}