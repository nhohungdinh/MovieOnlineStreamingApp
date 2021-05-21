package com.example.movieonlinestreamingapp.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.R;
import com.squareup.picasso.Picasso;

public class HanhDongViewHolder extends RecyclerView.ViewHolder {

    private ImageView movieGenreHanhDongImageView;
    public AppCompatTextView movieGenreHanhDongName;

    public HanhDongViewHolder(@NonNull View itemView) {
        super(itemView);
        movieGenreHanhDongImageView = itemView.findViewById(R.id.movie_genre_hanh_dong_image_view);
        movieGenreHanhDongName = itemView.findViewById(R.id.movie_genre_hanh_dong_name);
    }

    public void setMovieGenreHanhDongImageView(Context context, String posterUrl) {
        Picasso.get().load(posterUrl).into(movieGenreHanhDongImageView);
    }
}
