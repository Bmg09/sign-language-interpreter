package com.bikram.practice.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bikram.practice.GlossaryActivity;
import com.bikram.practice.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<String> letters;
    Context context;

    public RecyclerAdapter(Context ctx, List<String> letters) {
        context = ctx;
        this.letters = letters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridadapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (mListener != null) {
            mListener.onBindViewHolder(holder, position);
        }
    }


    @Override
    public int getItemCount() {
        return letters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView letter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            letter = itemView.findViewById(R.id.letterholder);

        }

        public TextView getLetter() {
            return letter;
        }
    }
    public interface OnBindViewHolderListener {
        void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position);
    }
    private OnBindViewHolderListener mListener;
    public void setOnBindViewHolderListener(OnBindViewHolderListener listener) {
        mListener = listener;
    }
}
