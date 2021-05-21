package com.example.movieonlinestreamingapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.Activities.TrailerPlayActivity;
import com.example.movieonlinestreamingapp.Helpers.MovieTrailersViewHolder;
import com.example.movieonlinestreamingapp.Models.Trailer;
import com.example.movieonlinestreamingapp.R;

import java.util.ArrayList;
import java.util.List;

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersViewHolder> {
    private Activity activity;
    private List<Trailer> trailerList;

    public MovieTrailersAdapter(Activity activity, List<Trailer> trailerList) {
        this.activity = activity;
        this.trailerList = trailerList;
    }

    @NonNull
    @Override
    public MovieTrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.trailers_layout, parent, false);

        return new MovieTrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailersViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        String baseUrl = "https://www.youtube.com/watch?v=";
        String trailerUrl = baseUrl + trailer.getTrailerUrl();
        holder.setThumbnailView(activity,trailerUrl);
        holder.trailerTitle.setText(trailer.getTrailerName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Trailer> trailerArrayList = new ArrayList<>(trailerList);

                Intent intent = new Intent(activity, TrailerPlayActivity.class);

                // set animation to open the video
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,holder.thumbnailView, ViewCompat.getTransitionName(holder.thumbnailView));

                // put the current video position and video list

                intent.putExtra("position",String.valueOf(holder.getAdapterPosition()));
                intent.putExtra("trailer", trailerArrayList);

                activity.startActivity(intent,compat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }
}
