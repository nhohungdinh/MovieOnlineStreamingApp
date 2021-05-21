package com.example.movieonlinestreamingapp.Models;

import java.util.List;

public class MovieResponse {
    private List<MovieResult> movieResult;

    public MovieResponse(List<MovieResult> movieResult) {
        this.movieResult = movieResult;
    }

    public MovieResponse() {
    }

    public List<MovieResult> getMovieResult() {
        return movieResult;
    }

    public void setMovieResult(List<MovieResult> movieResult) {
        this.movieResult = movieResult;
    }
}
