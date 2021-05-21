package com.example.movieonlinestreamingapp.Helpers;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.R;

public class GenreViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView genreName;

    public GenreViewHolder(@NonNull View itemView) {
        super(itemView);
        genreName = itemView.findViewById(R.id.genre_name);
    }
}
