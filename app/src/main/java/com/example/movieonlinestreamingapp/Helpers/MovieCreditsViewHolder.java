package com.example.movieonlinestreamingapp.Helpers;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

public class MovieCreditsViewHolder extends RecyclerView.ViewHolder {

    private KenBurnsView movieCreditsImageView;
    public AppCompatTextView movieCreditsName;


    public MovieCreditsViewHolder(@NonNull View itemView) {
        super(itemView);
        movieCreditsImageView = itemView.findViewById(R.id.movie_credits_image_view);
        movieCreditsName = itemView.findViewById(R.id.movie_credits_name);
    }

    public void setMovieCreditsImageView(Context context,String imageUrl) {
        Picasso.get().load(imageUrl).into(movieCreditsImageView);
    }
}
