package com.example.movieonlinestreamingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {
    private int id;

    private String trailerUrl;

    private String trailerName;

    private int movieId;

    protected Trailer(Parcel in) {
        id = in.readInt();
        trailerUrl = in.readString();
        trailerName = in.readString();
        movieId = in.readInt();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Trailer(int id, String trailerUrl, String trailerName, int movieId) {
        this.id = id;
        this.trailerUrl = trailerUrl;
        this.trailerName = trailerName;
        this.movieId = movieId;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public Trailer() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(trailerUrl);
        dest.writeString(trailerName);
        dest.writeInt(movieId);
    }
}
