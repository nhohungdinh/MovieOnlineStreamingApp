package com.example.movieonlinestreamingapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.Activities.MovieDetailActivity;
import com.example.movieonlinestreamingapp.Helpers.HaiKichViewHolder;
import com.example.movieonlinestreamingapp.Models.MovieResult;
import com.example.movieonlinestreamingapp.R;

import java.util.List;

public class MovieGenreHaiKichAdapter extends RecyclerView.Adapter<HaiKichViewHolder> {
    private Activity activity;
    private List<MovieResult> movieResults;

    public MovieGenreHaiKichAdapter(Activity activity, List<MovieResult> movieResults) {
        this.activity = activity;
        this.movieResults = movieResults;
    }

    @NonNull
    @Override
    public HaiKichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.movie_genre_hai_kich_layout, parent, false);
        return new HaiKichViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HaiKichViewHolder holder, int position) {
        final MovieResult result = movieResults.get(position);
        holder.setMovieGenreHaiKichImageView(activity,result.getPosterPath());
        String title = result.getTitle();
        if (title != null){
            holder.movieGenreHaiKichName.setVisibility(View.VISIBLE);
            holder.movieGenreHaiKichName.setText(title);
        }
        else {
            holder.movieGenreHaiKichName.setVisibility(View.GONE);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MovieDetailActivity.class);
                intent.putExtra("id",String.valueOf(result.getId()));
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieResults.size();
    }
}
