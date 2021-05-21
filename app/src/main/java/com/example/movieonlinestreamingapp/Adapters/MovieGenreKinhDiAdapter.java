package com.example.movieonlinestreamingapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlinestreamingapp.Activities.MovieDetailActivity;
import com.example.movieonlinestreamingapp.Helpers.KinhDiViewHolder;
import com.example.movieonlinestreamingapp.Models.MovieResult;
import com.example.movieonlinestreamingapp.R;

import java.util.List;

public class MovieGenreKinhDiAdapter extends RecyclerView.Adapter<KinhDiViewHolder> {
    private Activity activity;
    private List<MovieResult> movieResults;

    public MovieGenreKinhDiAdapter(Activity activity, List<MovieResult> movieResults) {
        this.activity = activity;
        this.movieResults = movieResults;
    }

    @NonNull
    @Override
    public KinhDiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.movie_genre_kinh_di_layout, parent, false);
        return new KinhDiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KinhDiViewHolder holder, int position) {
        final MovieResult result = movieResults.get(position);
        holder.setMovieGenreKinhDiImageView(activity,result.getPosterPath());
        String title = result.getTitle();
        if (title != null){
            holder.movieGenreKinhDiName.setVisibility(View.VISIBLE);
            holder.movieGenreKinhDiName.setText(title);
        }
        else {
            holder.movieGenreKinhDiName.setVisibility(View.GONE);

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
