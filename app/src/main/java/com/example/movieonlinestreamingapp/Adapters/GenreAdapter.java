package com.example.movieonlinestreamingapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.Activities.HomeActivity;
import com.example.movieonlinestreamingapp.Helpers.GenreViewHolder;
import com.example.movieonlinestreamingapp.Models.Genre;
import com.example.movieonlinestreamingapp.R;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreViewHolder> {

    private Activity activity;
    private List<Genre> genreList;

    public GenreAdapter(Activity activity, List<Genre> genreList) {
        this.activity = activity;
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.genre_layout_item, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        final Genre genre = genreList.get(position);
        holder.genreName.setText(genre.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }
}
