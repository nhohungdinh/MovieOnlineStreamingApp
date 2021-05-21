package com.example.movieonlinestreamingapp.Models;

import java.util.List;

public class MovieResult {
    private int id;

    private String overview;

    private String posterPath;

    private String releaseDate;

    private String title;

    private String videoUrl;

    private Double voteAverage;

    private String genreCode;

    List<Actor> actors;

    List<Trailer> trailers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        // create baseurl for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getGenreCode() {
        return genreCode;
    }

    public void setGenreCode(String genreCode) {
        this.genreCode = genreCode;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public MovieResult(int id, String overview, String posterPath, String releaseDate, String title, String videoUrl, Double voteAverage, String genreCode, List<Actor> actors, List<Trailer> trailers) {
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.videoUrl = videoUrl;
        this.voteAverage = voteAverage;
        this.genreCode = genreCode;
        this.actors = actors;
        this.trailers = trailers;
    }

    public MovieResult() {
    }
}
