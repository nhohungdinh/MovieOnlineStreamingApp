package com.example.movieonlinestreamingapp.Models;

import java.util.List;

public class MovieTrailers {
    List<Trailer> trailerResult;

    public MovieTrailers() {
    }

    public MovieTrailers(List<Trailer> trailerResult) {
        this.trailerResult = trailerResult;
    }

    public List<Trailer> getTrailerResult() {
        return trailerResult;
    }

    public void setTrailerResult(List<Trailer> trailerResult) {
        this.trailerResult = trailerResult;
    }
}
