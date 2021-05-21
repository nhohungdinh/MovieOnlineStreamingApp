package com.example.movieonlinestreamingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.example.movieonlinestreamingapp.R;
import com.example.movieonlinestreamingapp.Utils.FullScreen;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

public class MoviePlayActivity extends AppCompatActivity {

    private ThumbnailView thumbnailView;
    private YouTubePlayerView playerView;
    private FullScreen fullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_play);

        Intent intent = getIntent();
        ThumbnailLoader.initialize("AIzaSyCx0vMCP7sQKIfnXglxHw9CJS_Bo3Ablls");
        fullScreen = new FullScreen(this);
        thumbnailView = findViewById(R.id.videos_thumbnail_view);
        playerView = findViewById(R.id.videos_player_view);
        AppCompatTextView videosTitle = findViewById(R.id.play_videos_title);

        if (intent != null && intent.getExtras() != null)
        {
            String videoUrl = intent.getExtras().getString("video");
            String title = intent.getExtras().getString("title");
            if (title != null){
                videosTitle.setText(title);
            }
            if (videoUrl != null){
                String baseUrl = "https://www.youtube.com/watch?v=";
                thumbnailView.loadThumbnail(baseUrl + videoUrl);

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
                                    youTubePlayer.loadVideo(videoUrl, 0);
                                } else {
                                    youTubePlayer.cueVideo(videoUrl, 0);
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
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (playerView.isFullScreen())
        {
            playerView.exitFullScreen();
        }
        else
        {
            playerView.setVisibility(View.GONE);
            thumbnailView.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }
    }
}