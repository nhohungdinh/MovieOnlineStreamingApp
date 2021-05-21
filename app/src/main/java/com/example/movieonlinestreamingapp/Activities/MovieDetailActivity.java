package com.example.movieonlinestreamingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.example.movieonlinestreamingapp.Adapters.MovieCreditsCastAdapter;
import com.example.movieonlinestreamingapp.Adapters.MovieTrailersAdapter;
import com.example.movieonlinestreamingapp.Client.RetrofitClient;
import com.example.movieonlinestreamingapp.Interfaces.RetrofitService;
import com.example.movieonlinestreamingapp.Models.Actor;
import com.example.movieonlinestreamingapp.Models.MovieCredits;
import com.example.movieonlinestreamingapp.Models.MovieResult;
import com.example.movieonlinestreamingapp.Models.MovieTrailers;
import com.example.movieonlinestreamingapp.Models.Trailer;
import com.example.movieonlinestreamingapp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private KenBurnsView movieDetailPosterImageView;
    private CircleImageView movieDetailPosterCircleImageView;
    private ArcProgress movieDetailRatingBar;

    private AppCompatButton movieDetailWatchBtn;
    private AppCompatButton movieDetailCommentBtn;

    private LinearLayoutCompat movieDetailOriginalTitleLayout;
    private LinearLayoutCompat movieDetailReleaseDateLayout;
    private LinearLayoutCompat movieDetailOverviewLayout;
    private LinearLayoutCompat movieDetailCastLayout;
    private LinearLayoutCompat movieDetailVideosLayout;

    private AppCompatTextView movieDetailOriginalTitle;
    private AppCompatTextView movieDetailReleaseDate;
    private AppCompatTextView movieDetailOverview;
    private AppCompatTextView movieDetailTitle;

    private RecyclerView movieDetailCastRecycleView;
    private RecyclerView movieDetailVideosRecycleview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();

        RetrofitService retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        ThumbnailLoader.initialize("AIzaSyCx0vMCP7sQKIfnXglxHw9CJS_Bo3Ablls");

        movieDetailPosterImageView = findViewById(R.id.movie_detail_poster_image_view);
        movieDetailPosterCircleImageView = findViewById(R.id.movie_detail_poster_circle_image_view);
        movieDetailRatingBar = findViewById(R.id.movie_detail_rating_bar);

        movieDetailWatchBtn = findViewById(R.id.movie_detail_watch_btn);
        movieDetailCommentBtn = findViewById(R.id.movie_deltail_comment_btn);

        movieDetailOriginalTitleLayout = findViewById(R.id.movie_detail_original_title_layout);
        movieDetailReleaseDateLayout = findViewById(R.id.movie_detail_release_date_layout);
        movieDetailOverviewLayout = findViewById(R.id.movie_detail_overview_layout);
        movieDetailCastLayout = findViewById(R.id.movie_detail_cast_layout);
        movieDetailVideosLayout = findViewById(R.id.movie_detail_videos_layout);

        movieDetailOriginalTitle = findViewById(R.id.movie_detail_original_title);
        movieDetailReleaseDate = findViewById(R.id.movie_detail_release_date);
        movieDetailOverview = findViewById(R.id.movie_detail_overview);
        movieDetailTitle = findViewById(R.id.movie_detail_title);

        movieDetailCastRecycleView = findViewById(R.id.movie_detail_cast_recycle_view);
        movieDetailCastRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        movieDetailVideosRecycleview = findViewById(R.id.movie_detail_videos_recycle_view);
        movieDetailVideosRecycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (intent != null && intent.getExtras() != null){
            if (intent.getExtras().getString("id") != null){
                int id = Integer.parseInt(intent.getExtras().getString("id"));

                movieDetailWatchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<MovieResult> movieResultCall = retrofitService.getMovieByMovieId(id);
                        movieResultCall.enqueue(new Callback<MovieResult>() {
                            @Override
                            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                                MovieResult movieResultResponse = response.body();
                                if (movieResultResponse != null){

                                    String title = movieResultResponse.getTitle();
                                    String videoUrl = movieResultResponse.getVideoUrl();
                                    Intent intent1 = new Intent(MovieDetailActivity.this,MoviePlayActivity.class);

                                    intent1.putExtra("video", videoUrl);
                                    intent1.putExtra("title", title);
                                    startActivity(intent1);

                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {

                            }
                        });
                    }
                });

                Call<MovieResult> movieResultCall = retrofitService.getMovieByMovieId(id);
                movieResultCall.enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                        MovieResult movieResultResponse = response.body();
                        if (movieResultResponse != null){
                            prepareMovieDetail(movieResultResponse);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {

                    }
                });

                Call<MovieCredits> movieCreditsCall = retrofitService.getMovieCreditsByMovieId(id);
                movieCreditsCall.enqueue(new Callback<MovieCredits>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieCredits> call,@NonNull Response<MovieCredits> response) {
                        MovieCredits movieCreditsResponse = response.body();
                        if (movieCreditsResponse != null){
                            List<Actor> movieCreditsCastList = movieCreditsResponse.getActorResult();

                            if (movieCreditsCastList != null && movieCreditsCastList.size() > 0){
                                MovieCreditsCastAdapter movieCreditsCastAdapter = new MovieCreditsCastAdapter(MovieDetailActivity.this, movieCreditsCastList);
                                movieDetailCastRecycleView.setAdapter(movieCreditsCastAdapter);
                                movieDetailCastLayout.setVisibility(View.VISIBLE);
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this,R.anim.layout_slide_right);
                                movieDetailCastRecycleView.setLayoutAnimation(controller);
                                movieDetailCastRecycleView.scheduleLayoutAnimation();
                            }
                            else {
                                movieDetailCastLayout.setVisibility(View.GONE);
                            }
                        }
                        else {
                            movieDetailCastLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieCredits> call,@NonNull Throwable t) {

                    }
                });
                Call<MovieTrailers> movieTrailersCall = retrofitService.getTrailerByMovieId(id);

                movieTrailersCall.enqueue(new Callback<MovieTrailers>() {
                    @Override
                    public void onResponse(Call<MovieTrailers> call, Response<MovieTrailers> response) {
                        MovieTrailers movieTrailers = response.body();
                        if (movieTrailers != null)
                        {
                            List<Trailer> trailerResult = movieTrailers.getTrailerResult();
                            if (trailerResult != null && trailerResult.size() > 0)
                            {
                                movieDetailVideosLayout.setVisibility(View.VISIBLE);
                                MovieTrailersAdapter adapter = new MovieTrailersAdapter(MovieDetailActivity.this,trailerResult);

                                movieDetailVideosRecycleview.setAdapter(adapter);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_bottom);
                                movieDetailVideosRecycleview.setLayoutAnimation(controller);
                                movieDetailVideosRecycleview.scheduleLayoutAnimation();
                            }
                            else
                            {
                                movieDetailVideosLayout.setVisibility(View.GONE);
                            }
                        }
                        else
                        {
                            movieDetailVideosLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieTrailers> call, Throwable t) {

                    }
                });
            }
        }
    }

    private void prepareMovieDetail(MovieResult movieResultResponse) {
        Double voteAverage = movieResultResponse.getVoteAverage() * 10;

        String title = movieResultResponse.getTitle();
        String originalTitle = movieResultResponse.getTitle();
        String releaseDate = movieResultResponse.getReleaseDate();
        String overview = movieResultResponse.getOverview();
        String posterPath = movieResultResponse.getPosterPath();
        String backdropPath = movieResultResponse.getPosterPath();

        Picasso.get().load(posterPath).into(movieDetailPosterImageView);
        Picasso.get().load(backdropPath).into(movieDetailPosterCircleImageView);

        int rating = voteAverage.intValue();
        movieDetailRatingBar.setProgress(rating);

        movieDetailTitle.setText(title);
        if (originalTitle != null){
            if (originalTitle.length() > 0){
                movieDetailOriginalTitle.setText(title);
                movieDetailOriginalTitleLayout.setVisibility(View.VISIBLE);
            }
            else {
                movieDetailOriginalTitleLayout.setVisibility(View.GONE);
            }
        } else {
            movieDetailOriginalTitleLayout.setVisibility(View.GONE);
        }
        if (releaseDate != null){
            if (originalTitle.length() > 0){
                movieDetailReleaseDate.setText(releaseDate);
                movieDetailReleaseDateLayout.setVisibility(View.VISIBLE);
            }
            else {
                movieDetailReleaseDateLayout.setVisibility(View.GONE);
            }
        } else {
            movieDetailReleaseDateLayout.setVisibility(View.GONE);
        }
        if (overview != null){
            if (overview.length() > 0){
                movieDetailOverview.setText(overview);
                movieDetailOverviewLayout.setVisibility(View.VISIBLE);
            }
            else {
                movieDetailOverviewLayout.setVisibility(View.GONE);
            }
        } else {
            movieDetailOverviewLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}