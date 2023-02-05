package com.bikram.practice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.getLetter().setText(letters.get(position));
//        holder.getCardView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,CardViewerSwipeable.class);
//                intent.putExtra("t",String.valueOf(position));
//                context.startActivity(intent);
//            }
//        });
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

        public CardView getCardView() {
            return cardView;
        }

        public TextView getLetter() {
            return letter;
        }
    }
}
