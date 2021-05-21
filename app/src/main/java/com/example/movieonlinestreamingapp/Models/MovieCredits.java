package com.example.movieonlinestreamingapp.Models;

import java.util.List;

public class MovieCredits {
    List<Actor> actorResult;

    public MovieCredits() {
    }

    public MovieCredits(List<Actor> actorResult) {
        this.actorResult = actorResult;
    }

    public List<Actor> getActorResult() {
        return actorResult;
    }

    public void setActorResult(List<Actor> actorResult) {
        this.actorResult = actorResult;
    }
}
