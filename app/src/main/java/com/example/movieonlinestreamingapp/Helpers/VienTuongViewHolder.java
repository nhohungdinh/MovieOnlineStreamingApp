package com.example.movieonlinestreamingapp.Helpers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.R;
import com.squareup.picasso.Picasso;

public class VienTuongViewHolder extends RecyclerView.ViewHolder {

    private ImageView movieGenreVienTuongImageView;
    public AppCompatTextView movieGenreVienTuongName;
    public VienTuongViewHolder(@NonNull View itemView) {
        super(itemView);
        movieGenreVienTuongImageView = itemView.findViewById(R.id.movie_genre_vien_tuong_image_view);
        movieGenreVienTuongName = itemView.findViewById(R.id.movie_genre_vien_tuong_name);
    }

    public void setMovieGenreVienTuongImageView(Context context, String posterUrl) {
        Picasso.get().load(posterUrl).into(movieGenreVienTuongImageView);
    }

}
