package com.example.movieonlinestreamingapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.Activities.MovieDetailActivity;
import com.example.movieonlinestreamingapp.Helpers.MovieSearchViewHolder;
import com.example.movieonlinestreamingapp.Models.MovieResult;
import com.example.movieonlinestreamingapp.R;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchViewHolder> {

    private Activity activity;
    private List<MovieResult> movieResult;

    public MovieSearchAdapter(Activity activity, List<MovieResult> movieResult) {
        this.activity = activity;
        this.movieResult = movieResult;
    }

    @NonNull
    @Override
    public MovieSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.search_layout_items, parent, false);
        return new MovieSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSearchViewHolder holder, int position) {
        final MovieResult result = movieResult.get(position);
        holder.setPosterImageView(activity,result.getPosterPath());
        String title = result.getTitle();
        if (title != null){
            holder.posterTitle.setVisibility(View.VISIBLE);
            holder.posterTitle.setText(title);
        }
        else {
            holder.posterTitle.setVisibility(View.GONE);

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
        return movieResult.size();
    }
}
