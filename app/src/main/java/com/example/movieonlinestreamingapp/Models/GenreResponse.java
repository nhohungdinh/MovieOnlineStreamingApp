package com.example.movieonlinestreamingapp.Models;

import java.util.List;

public class GenreResponse {
    List<Genre> genreResult;

    public GenreResponse() {
    }

    public GenreResponse(List<Genre> genreResult) {
        this.genreResult = genreResult;
    }

    public List<Genre> getGenreResult() {
        return genreResult;
    }

    public void setGenreResult(List<Genre> genreResult) {
        this.genreResult = genreResult;
    }
}
