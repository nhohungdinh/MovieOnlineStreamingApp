package com.example.movieonlinestreamingapp.Interfaces;

import com.example.movieonlinestreamingapp.Models.Actor;
import com.example.movieonlinestreamingapp.Models.MovieCredits;
import com.example.movieonlinestreamingapp.Models.MovieResponse;
import com.example.movieonlinestreamingapp.Models.MovieResult;
import com.example.movieonlinestreamingapp.Models.MovieTrailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    // https://api.themoviedb.org/3/search/movie?api_key="API_Key"&query="MOVIE_NAME"
    // this is url to get the movie results
    // https://api.themoviedb.org/3/search/movie?api_key="API_Key"&query="ACTOR_NAME"
    // this is url to get the actor results
    // https://api.themoviedb.org/3/
    // this is base url, 've already created in client
    // now create a service to get results and convert results into model classes
    @GET("movie")
    Call<MovieResponse> getMovieByActorName(@Query("actorname") String actorname);

    @GET("movie/genre/{id}")
    Call<MovieResponse> getMovieByGenre(@Path("id") int id);

    @GET("actor/movie/{id}")
    Call<MovieCredits> getMovieCreditsByMovieId(@Path("id") int id);

    @GET("movie/{title}")
    Call<MovieResponse> getMovieByTitle(@Path("title") String title);

    @GET("/movie/id/{id}")
    Call<MovieResult> getMovieByMovieId(@Path("id") int id);

    @GET("/actor/{id}")
    Call<Actor> getActorById(@Path("id") int id);

    @GET("/trailer/movie/{id}")
    Call<MovieTrailers> getTrailerByMovieId(@Path("id") int id);
}
