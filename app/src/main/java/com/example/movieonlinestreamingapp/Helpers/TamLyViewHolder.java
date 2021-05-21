package com.example.movieonlinestreamingapp.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.R;
import com.squareup.picasso.Picasso;

public class TamLyViewHolder extends RecyclerView.ViewHolder {
    private ImageView movieGenreTamLyImageView;
    public AppCompatTextView movieGenreTamLyName;

    public TamLyViewHolder(@NonNull View itemView) {
        super(itemView);
        movieGenreTamLyImageView = itemView.findViewById(R.id.movie_genre_tam_ly_image_view);
        movieGenreTamLyName = itemView.findViewById(R.id.movie_genre_tam_ly_name);
    }

    public void setMovieGenreTamLyImageView(Context context, String posterUrl) {
        Picasso.get().load(posterUrl).into(movieGenreTamLyImageView);
    }
}
