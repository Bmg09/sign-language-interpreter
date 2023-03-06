package com.bikram.practice.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bikram.practice.LoginActivity;
import com.bikram.practice.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    GoogleSignInAccount googleSignIn;
    GoogleSignInClient googleSignInClient;
    ImageView imageView;
    FirebaseUser mUser = auth.getCurrentUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acount, container, false);
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

// Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Account");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
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
        imageView = view.findViewById(R.id.imag);
        TextView ttname = view.findViewById(R.id.fetchname);
        TextView ttemail = view.findViewById(R.id.fetchname2);
        TextView ttnc = view.findViewById(R.id.fetchname3);
        if (mUser != null) {
            String name = mUser.getDisplayName();
            String email = mUser.getEmail();
            Uri photoUrl = mUser.getPhotoUrl();

            // Use the user's name, email, and photo
            // ...
//            Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(), photoUrl.toString(), Toast.LENGTH_SHORT).show();
            Picasso.get().load(photoUrl).into(imageView);
            ttname.setText(name);
            ttemail.setText(email);


        } else {
            // User is not signed in
            // ...
        }
        ttnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://bmg09.github.io/slitnc/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(Menu.NONE, 1, Menu.NONE, "Sign out");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                auth.signOut();
                boolean issigned = GoogleSignIn.getLastSignedInAccount(getContext()) != null;
                if (issigned) {
                    googleSignInClient.signOut();
                }
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
        return true;
    }
}