package com.example.movieonlinestreamingapp.Models;

import java.util.List;

public class Genre {
    private int id;
    private String name;
    private String code;
    private List<MovieResult> movieResult;

    public Genre() {
    }

    public Genre(int id, String name, String code, List<MovieResult> movieResult) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.movieResult = movieResult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MovieResult> getMovieResult() {
        return movieResult;
    }

    public void setMovieResult(List<MovieResult> movieResult) {
        this.movieResult = movieResult;
    }
}
