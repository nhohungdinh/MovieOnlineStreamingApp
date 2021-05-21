package com.example.movieonlinestreamingapp.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.R;
import com.squareup.picasso.Picasso;

public class HaiKichViewHolder extends RecyclerView.ViewHolder {
    private ImageView movieGenreHaiKichImageView;
    public AppCompatTextView movieGenreHaiKichName;
    public HaiKichViewHolder(@NonNull View itemView) {
        super(itemView);
        movieGenreHaiKichImageView = itemView.findViewById(R.id.movie_genre_hai_kich_image_view);
        movieGenreHaiKichName = itemView.findViewById(R.id.movie_genre_hai_kich_name);
    }
    public void setMovieGenreHaiKichImageView(Context context, String posterUrl) {
        Picasso.get().load(posterUrl).into(movieGenreHaiKichImageView);
    }
}
