package com.example.android.popularmovies.data;

import java.io.Serializable;

/**
 * Class Movie: contains informations about a movie
 * <p>
 * - original title
 * - movie poster image thumbnail
 * - A plot synopsis (called overview in the api)
 * - user rating (called vote_average in the api)
 * - release date
 * <p>
 * Created by lsitec335.takayama on 15/08/17.
 */

public class Movie implements Serializable {

    private long mId;
    private String mTitle;
    private String mPosterUrl;
    private String mSynopsis;
    private double mRating;
    private String mReleaseDate;

    public Movie(long id, String title, String posterUrl, String sysnopsis,
                 double rating, String releaseDate) {
        setId(id);
        setTitle(title);
        setPosterUrl(posterUrl);
        setSynopsis(sysnopsis);
        setRating(rating);
        setReleaseDate(releaseDate);
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) { this.mId = id; }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.mPosterUrl = posterUrl;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        this.mSynopsis = synopsis;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        this.mRating = rating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie:{id:" + getId() +
                ",title:" + getTitle() +
                ",posterUrl:" + getPosterUrl() +
                ",synopsis:" + getSynopsis() +
                ",rating:" + getRating() +
                ",releaseDate:" + getReleaseDate() +
                "}";
    }
}
