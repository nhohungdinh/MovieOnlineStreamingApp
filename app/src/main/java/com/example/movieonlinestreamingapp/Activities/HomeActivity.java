package com.example.movieonlinestreamingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieonlinestreamingapp.Adapters.MovieGenreHaiKichAdapter;
import com.example.movieonlinestreamingapp.Adapters.MovieGenreHanhDongAdapter;
import com.example.movieonlinestreamingapp.Adapters.MovieGenreKinhDiAdapter;
import com.example.movieonlinestreamingapp.Adapters.MovieGenreTamLyAdapter;
import com.example.movieonlinestreamingapp.Adapters.MovieGenreToiPhamAdapter;
import com.example.movieonlinestreamingapp.Adapters.MovieGenreVienTuongAdapter;
import com.example.movieonlinestreamingapp.Adapters.MovieSearchAdapter;
import com.example.movieonlinestreamingapp.Client.RetrofitClient;
import com.example.movieonlinestreamingapp.Interfaces.RetrofitService;
import com.example.movieonlinestreamingapp.Models.MovieResponse;
import com.example.movieonlinestreamingapp.Models.MovieResult;
import com.example.movieonlinestreamingapp.R;
import com.google.gson.Gson;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private NiceSpinner sourceSpinner;
    private EditText queryEditText;
    private Button querySearchButton;
    private String movie = "By Movie Title";
    private String person = "By Person Name";
    private RecyclerView resultsRecyclerView;

    private RecyclerView genreGenreHanhDongRecycleView;
    private RecyclerView genreGenreVienTuongRecycleView;
    private RecyclerView genreGenreTamLyRecycleView;
    private RecyclerView genreGenreToiPhamRecycleView;
    private RecyclerView genreGenreHaiKichRecycleView;
    private RecyclerView genreGenreKinhDiRecycleView;

    private LinearLayoutCompat genreHanhDongResultLayout;
    private LinearLayoutCompat genreVienTuongResultLayout;
    private LinearLayoutCompat genreTamLyResultLayout;
    private LinearLayoutCompat genreToiPhamResultLayout;
    private LinearLayoutCompat genreHaiKichResultLayout;
    private LinearLayoutCompat genreKinhDiResultLayout;

    private RetrofitService retrofitService;

    private MovieSearchAdapter movieSearchAdapter;
    private MovieGenreHanhDongAdapter movieGenreHanhDongAdapter;
    private MovieGenreVienTuongAdapter movieGenreVienTuongAdapter;
    private MovieGenreTamLyAdapter movieGenreTamLyAdapter;
    private MovieGenreToiPhamAdapter movieGenreToiPhamAdapter;
    private MovieGenreHaiKichAdapter movieGenreHaiKichAdapter;
    private MovieGenreKinhDiAdapter movieGenreKinhDiAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // diable keyword on start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sourceSpinner = findViewById(R.id.source_spinner);
        queryEditText = findViewById(R.id.query_edit_text);
        querySearchButton = findViewById(R.id.query_search_button);
        resultsRecyclerView = findViewById(R.id.results_recycle_view);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        genreGenreHanhDongRecycleView = findViewById(R.id.genre_hanh_dong_result_recycle_view);
        genreGenreHanhDongRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        genreGenreVienTuongRecycleView = findViewById(R.id.movie_genre_vien_tuong_result_recycle_view);
        genreGenreVienTuongRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        genreGenreTamLyRecycleView = findViewById(R.id.movie_genre_tam_ly_result_recycle_view);
        genreGenreTamLyRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        genreGenreToiPhamRecycleView = findViewById(R.id.movie_genre_toi_pham_result_recycle_view);
        genreGenreToiPhamRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        genreGenreHaiKichRecycleView = findViewById(R.id.movie_genre_hai_kich_result_recycle_view);
        genreGenreHaiKichRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        genreGenreKinhDiRecycleView = findViewById(R.id.movie_genre_kinh_di_result_recycle_view);
        genreGenreKinhDiRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        genreHanhDongResultLayout = findViewById(R.id.genre_hanh_dong_result_layout);
        genreVienTuongResultLayout = findViewById(R.id.genre_vien_tuong_result_layout);
        genreTamLyResultLayout = findViewById(R.id.genre_tam_ly_result_layout);
        genreToiPhamResultLayout = findViewById(R.id.genre_toi_pham_result_layout);
        genreHaiKichResultLayout = findViewById(R.id.genre_hai_kich_result_layout);
        genreKinhDiResultLayout = findViewById(R.id.genre_kinh_di_result_layout);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.ic_baseline_menu_24);

        Paper.init(this);
        retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        final ArrayList<String> category = new ArrayList<>();

        // set list for sourceSpinner

        // person name means actor
        category.add(movie);
        category.add(person);

        sourceSpinner.attachDataSource(category);

        // retrieve the position at start and the set  the spinner
        if (Paper.book().read("position") != null){
            int position = Paper.book().read("position");
            sourceSpinner.setSelectedIndex(position);
        }

        // set the text on edit text on create
        int position = sourceSpinner.getSelectedIndex();
        if (position == 0) {
            queryEditText.setHint("Enter movie title here..");
        } else {
            queryEditText.setHint("Enter any person name..");
        }
        sourceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                // when sourceSpinner in clicked change the text of the edit text
                if (position == 0) {
                    queryEditText.setHint("Enter movie title here..");
                } else {
                    queryEditText.setHint("Enter any person name..");
                }
            }
        });

        // retrieve the results from Paperdb and start
        if (Paper.book().read("cache") != null){
            String results = Paper.book().read("cache");
            if (Paper.book().read("source") != null){
                String source = Paper.book().read("source");
                if (source.equals("movie")){
                    // convert the string cache to model movie response using gson
                    MovieResponse movieResponse = new Gson().fromJson(results, MovieResponse.class);
                    if (movieResponse != null){
                        List<MovieResult> movieResults = movieResponse.getMovieResult();
                        movieSearchAdapter = new MovieSearchAdapter(HomeActivity.this, movieResults);
                        resultsRecyclerView.setAdapter(movieSearchAdapter);

                        // create some animation to recycle view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this,R.anim.layout_slide_right);
                        resultsRecyclerView.setLayoutAnimation(controller);
                        resultsRecyclerView.scheduleLayoutAnimation();

                        // now store the results in paper database to access offline
                        Paper.book().write("cache", new Gson().toJson(movieResponse));
                        // store also the category to set the spinner at app start
                        Paper.book().write("source","movie");
                    }
                }
                else {
                    MovieResponse movieResponse = new Gson().fromJson(results, MovieResponse.class);
                    if (movieResponse != null){
                        List<MovieResult> movieResults = movieResponse.getMovieResult();
                        movieSearchAdapter = new MovieSearchAdapter(HomeActivity.this, movieResults);
                        resultsRecyclerView.setAdapter(movieSearchAdapter);

                        // create some animation to recycle view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this,R.anim.layout_slide_right);
                        resultsRecyclerView.setLayoutAnimation(controller);
                        resultsRecyclerView.scheduleLayoutAnimation();

                        // now store the results in paper database to access offline
                        Paper.book().write("cache", new Gson().toJson(movieResponse));
                        // store also the category to set the spinner at app start
                        Paper.book().write("source","actor");
                    }
                }
            }
        }

        // get the query from user
        querySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (queryEditText.getText() != null) {
                    String query = queryEditText.getText().toString();
                    if (query.equals("") || query.equals(" ")){
                        Toast.makeText(getApplicationContext(),"Please enter any text...",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        queryEditText.setText("");
//                        String finalQuery = query.replaceAll(" ","+");
                        if (category.size() > 0){
                            String categoryName = category.get(sourceSpinner.getSelectedIndex());
                            if (categoryName.equals(movie)){
                                Call<MovieResponse> movieResponseCall = retrofitService.getMovieByTitle(query);

                                movieResponseCall.enqueue(new Callback<MovieResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                                        MovieResponse movieResponse = response.body();

                                        if (movieResponse != null){
                                            List<MovieResult> movieResults = movieResponse.getMovieResult();
                                            movieSearchAdapter = new MovieSearchAdapter(HomeActivity.this, movieResults);
                                            resultsRecyclerView.setAdapter(movieSearchAdapter);

                                            // create some animation to recycle view item loading
                                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this,R.anim.layout_slide_right);
                                            resultsRecyclerView.setLayoutAnimation(controller);
                                            resultsRecyclerView.scheduleLayoutAnimation();

                                            // now store the results in paper database to access offline
                                            Paper.book().write("cache", new Gson().toJson(movieResponse));
                                            // store also the category to set the spinner at app start
                                            Paper.book().write("source","movie");
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

                                    }
                                });
                            }
                            else {
                                Call<MovieResponse> movieResponseCall = retrofitService.getMovieByActorName(query);
                                movieResponseCall.enqueue(new Callback<MovieResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                                        MovieResponse movieResponse = response.body();

                                        if (movieResponse != null){
                                            List<MovieResult> movieResults = movieResponse.getMovieResult();
                                            movieSearchAdapter = new MovieSearchAdapter(HomeActivity.this, movieResults);
                                            resultsRecyclerView.setAdapter(movieSearchAdapter);

                                            // create some animation to recycle view item loading
                                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this,R.anim.layout_slide_right);
                                            resultsRecyclerView.setLayoutAnimation(controller);
                                            resultsRecyclerView.scheduleLayoutAnimation();

                                            // now store the results in paper database to access offline
                                            Paper.book().write("cache", new Gson().toJson(movieResponse));
                                            // store also the category to set the spinner at app start
                                            Paper.book().write("source","actor");
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

        Call<MovieResponse> movieGenreHanhDongCall = retrofitService.getMovieByGenre(1);
        movieGenreHanhDongCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null){
                    List<MovieResult> movieResults = movieResponse.getMovieResult();
                    if (movieResults != null && movieResults.size() > 0){
                        movieGenreHanhDongAdapter = new MovieGenreHanhDongAdapter(HomeActivity.this, movieResults);
                        genreGenreHanhDongRecycleView.setAdapter(movieGenreHanhDongAdapter);
                        genreHanhDongResultLayout.setVisibility(View.VISIBLE);
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this,R.anim.layout_slide_right);
                        genreGenreHanhDongRecycleView.setLayoutAnimation(controller);
                        genreGenreHanhDongRecycleView.scheduleLayoutAnimation();
                    }
                   else {
                        genreHanhDongResultLayout.setVisibility(View.GONE);
                    }
                }
                else {
                    genreHanhDongResultLayout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

            }
        });

        Call<MovieResponse> movieGenreVienTuongCall = retrofitService.getMovieByGenre(2);
        movieGenreVienTuongCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null){
                    List<MovieResult> movieResults = movieResponse.getMovieResult();
                    if (movieResults != null && movieResults.size() > 0){
                        movieGenreVienTuongAdapter = new MovieGenreVienTuongAdapter(HomeActivity.this, movieResults);
                        genreGenreVienTuongRecycleView.setAdapter(movieGenreVienTuongAdapter);
                        genreVienTuongResultLayout.setVisibility(View.VISIBLE);
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this,R.anim.layout_slide_right);
                        genreGenreVienTuongRecycleView.setLayoutAnimation(controller);
                        genreGenreVienTuongRecycleView.scheduleLayoutAnimation();
                    }
                    else {
                        genreVienTuongResultLayout.setVisibility(View.GONE);
                    }
                }
                else {
                    genreVienTuongResultLayout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

            }
        });

        Call<MovieResponse> movieGenreTamLyCall = retrofitService.getMovieByGenre(3);
        movieGenreTamLyCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null){
                    List<MovieResult> movieResults = movieResponse.getMovieResult();
                    if (movieResults != null && movieResults.size() > 0){
                        movieGenreTamLyAdapter = new MovieGenreTamLyAdapter(HomeActivity.this, movieResults);
                        genreGenreTamLyRecycleView.setAdapter(movieGenreTamLyAdapter);
                        genreTamLyResultLayout.setVisibility(View.VISIBLE);
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this,R.anim.layout_slide_right);
                        genreGenreTamLyRecycleView.setLayoutAnimation(controller);
                        genreGenreTamLyRecycleView.scheduleLayoutAnimation();
                    }
                    else {
                        genreTamLyResultLayout.setVisibility(View.GONE);
                    }
                }
                else {
                    genreTamLyResultLayout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

            }
        });

        Call<MovieResponse> movieGenreToiPhamCall = retrofitService.getMovieByGenre(4);
        movieGenreToiPhamCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null){
                    List<MovieResult> movieResults = movieResponse.getMovieResult();
                    if (movieResults != null && movieResults.size() > 0){
                        movieGenreToiPhamAdapter = new MovieGenreToiPhamAdapter(HomeActivity.this, movieResults);
                        genreGenreToiPhamRecycleView.setAdapter(movieGenreToiPhamAdapter);
                        genreToiPhamResultLayout.setVisibility(View.VISIBLE);
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this,R.anim.layout_slide_right);
                        genreGenreToiPhamRecycleView.setLayoutAnimation(controller);
                        genreGenreToiPhamRecycleView.scheduleLayoutAnimation();
                    }
                    else {
                        genreToiPhamResultLayout.setVisibility(View.GONE);
                    }
                }
                else {
                    genreToiPhamResultLayout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

            }
        });

        Call<MovieResponse> movieGenreHaiKichCall = retrofitService.getMovieByGenre(5);
        movieGenreHaiKichCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null){
                    List<MovieResult> movieResults = movieResponse.getMovieResult();
                    if (movieResults != null && movieResults.size() > 0){
                        movieGenreHaiKichAdapter = new MovieGenreHaiKichAdapter(HomeActivity.this, movieResults);
                        genreGenreHaiKichRecycleView.setAdapter(movieGenreHaiKichAdapter);
                        genreHaiKichResultLayout.setVisibility(View.VISIBLE);
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this,R.anim.layout_slide_right);
                        genreGenreHaiKichRecycleView.setLayoutAnimation(controller);
                        genreGenreHaiKichRecycleView.scheduleLayoutAnimation();
                    }
                    else {
                        genreHaiKichResultLayout.setVisibility(View.GONE);
                    }
                }
                else {
                    genreHaiKichResultLayout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

            }
        });

        Call<MovieResponse> movieGenreKinhDiCall = retrofitService.getMovieByGenre(6);
        movieGenreKinhDiCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null){
                    List<MovieResult> movieResults = movieResponse.getMovieResult();
                    if (movieResults != null && movieResults.size() > 0){
                        movieGenreKinhDiAdapter = new MovieGenreKinhDiAdapter(HomeActivity.this, movieResults);
                        genreGenreKinhDiRecycleView.setAdapter(movieGenreKinhDiAdapter);
                        genreKinhDiResultLayout.setVisibility(View.VISIBLE);
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(HomeActivity.this,R.anim.layout_slide_right);
                        genreGenreKinhDiRecycleView.setLayoutAnimation(controller);
                        genreGenreKinhDiRecycleView.scheduleLayoutAnimation();
                    }
                    else {
                        genreKinhDiResultLayout.setVisibility(View.GONE);
                    }
                }
                else {
                    genreKinhDiResultLayout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // set the position of spinner in offline to retrieve at start
        Paper.book().write("position", sourceSpinner.getSelectedIndex());
    }
}