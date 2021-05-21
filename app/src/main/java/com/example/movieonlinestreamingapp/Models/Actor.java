package com.example.movieonlinestreamingapp.Models;

public class Actor {
    private int id;

    private String name;

    private String gender;

    private String profilePathUrl;

    private String birthOfDate;

    private String biography;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePathUrl() {
        // create baseurl for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl + profilePathUrl;
    }

    public void setProfilePathUrl(String profilePathUrl) {
        this.profilePathUrl = profilePathUrl;
    }

    public String getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(String birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Actor(int id, String name, String gender, String profilePathUrl, String birthOfDate, String biography) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.profilePathUrl = profilePathUrl;
        this.birthOfDate = birthOfDate;
        this.biography = biography;
    }

    public Actor() {
    }
}
