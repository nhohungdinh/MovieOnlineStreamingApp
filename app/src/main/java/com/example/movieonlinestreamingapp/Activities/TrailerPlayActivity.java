package com.example.movieonlinestreamingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;


import com.codewaves.youtubethumbnailview.BuildConfig;
import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.example.movieonlinestreamingapp.Adapters.ExtraTrailersRecycleAdapter;

import com.example.movieonlinestreamingapp.Models.Trailer;
import com.example.movieonlinestreamingapp.R;
import com.example.movieonlinestreamingapp.Utils.FullScreen;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;


import java.util.ArrayList;

public class TrailerPlayActivity extends AppCompatActivity {

    private ThumbnailView thumbnailView;

    private YouTubePlayerView playerView;

//    private ProgressBar progressBar;

    private RecyclerView otherTrailersRecyclerView;

    private FullScreen fullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_play);

        Intent intent = getIntent();

        ThumbnailLoader.initialize("AIzaSyCx0vMCP7sQKIfnXglxHw9CJS_Bo3Ablls");

        fullScreen = new FullScreen(this);
        thumbnailView = findViewById(R.id.trailer_thumbnail_view);

        playerView = findViewById(R.id.trailer_player_view);

        AppCompatTextView trailerTitle = findViewById(R.id.play_trailer_title);
        AppCompatTextView noResultsFound = findViewById(R.id.no_result_found);
        otherTrailersRecyclerView = findViewById(R.id.orther_trailer_recycle_view);
        otherTrailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        progressBar.findViewById(R.id.progress_bar_trailer);
//        progressBar.getIndeterminateDrawable().setColorFilter(0XFFFFFFFF, PorterDuff.Mode.MULTIPLY);


        // get the arraylist and position
        if (intent != null && intent.getExtras() != null) {
            ArrayList<Trailer> trailerArrayList = intent.getExtras().getParcelableArrayList("trailer");
            int position = Integer.parseInt(intent.getExtras().getString("position"));

            if (trailerArrayList != null && trailerArrayList.size() > 0) {
                String trailerId = trailerArrayList.get(position).getTrailerUrl();
                String title = trailerArrayList.get(position).getTrailerName();

                if (title != null) {
                    trailerTitle.setText(title);
                }

                if (trailerId != null) {
                    String baseUrl = "https://www.youtube.com/watch?v=";

                    thumbnailView.loadThumbnail(baseUrl + trailerId);

                    playerView.initialize(new YouTubePlayerInitListener() {
                        @Override
                        public void onInitSuccess(@NonNull YouTubePlayer youTubePlayer) {
                            youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady() {
                                    //   when video is ready to play hide the thumbnail and progress bar
                                    thumbnailView.setVisibility(View.GONE);

//                                    progressBar.setVisibility(View.GONE);

                                    playerView.setVisibility(View.VISIBLE);

                                    if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
                                        youTubePlayer.loadVideo(trailerId, 0);
                                    } else {
                                        youTubePlayer.cueVideo(trailerId, 0);
                                    }
                                }
                            });
                            playerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                                @Override
                                public void onYouTubePlayerEnterFullScreen() {
                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                    fullScreen.enterFullScreen();
                                }

                                @Override
                                public void onYouTubePlayerExitFullScreen() {
                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                    fullScreen.exitFullScreen();
                                }
                            });
                        }
                    }, true);

                    // load other trailer in recycle view

                    ArrayList<Trailer> trailerArray = new ArrayList<>(trailerArrayList);

                    // remove current trailer from list

                    trailerArray.remove(position);
                    if (trailerArray.size() > 0)
                    {
                        noResultsFound.setVisibility(View.GONE);
                        ExtraTrailersRecycleAdapter adapter = new ExtraTrailersRecycleAdapter(TrailerPlayActivity.this,trailerArray);
                        otherTrailersRecyclerView.setAdapter(adapter);
                        otherTrailersRecyclerView.setVisibility(View.VISIBLE);

                        // create animation for loading item
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_slide_bottom);
                        otherTrailersRecyclerView.setLayoutAnimation(controller);
                        otherTrailersRecyclerView.scheduleLayoutAnimation();
                    }
                    else
                    {
                        otherTrailersRecyclerView.setVisibility(View.GONE);
                        noResultsFound.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    // exit fullscreen on back pressed

    @Override
    public void onBackPressed() {
        if (playerView.isFullScreen())
        {
            playerView.exitFullScreen();
        }
        else
        {
            otherTrailersRecyclerView.setVisibility(View.GONE);
            playerView.setVisibility(View.GONE);
            thumbnailView.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }
    }
}