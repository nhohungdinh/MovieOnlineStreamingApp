package com.example.movieonlinestreamingapp.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.R;
import com.squareup.picasso.Picasso;

public class KinhDiViewHolder extends RecyclerView.ViewHolder {
    private ImageView movieGenreKinhDiImageView;
    public AppCompatTextView movieGenreKinhDiName;

    public KinhDiViewHolder(@NonNull View itemView) {
        super(itemView);
        movieGenreKinhDiImageView = itemView.findViewById(R.id.movie_genre_kinh_di_image_view);
        movieGenreKinhDiName = itemView.findViewById(R.id.movie_genre_kinh_di_name);
    }
    public void setMovieGenreKinhDiImageView(Context context, String posterUrl) {
        Picasso.get().load(posterUrl).into(movieGenreKinhDiImageView);
    }
}
