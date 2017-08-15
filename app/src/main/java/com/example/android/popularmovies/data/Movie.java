package com.example.android.popularmovies.data;

import java.util.Date;

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

public class Movie {

    private String mTitle;
    private String mPosterUrl;
    private String mSynopsis;
    private double mRating;
    private String mReleaseDate;

    public Movie(String title, String posterUrl, String sysnopsis,
                 double rating, String releaseDate) {
        setTitle(title);
        setPosterUrl(posterUrl);
        setSynopsis(sysnopsis);
        setRating(rating);
        setReleaseDate(releaseDate);
    }

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
        //return super.toString();
        String movieStr = "Movie:{title:" + getTitle() +
                ",posterUrl:" + getPosterUrl() +
                ",synopsis:" + getSynopsis() +
                ",rating:" + getRating() +
                ",releaseDate:" + getReleaseDate() +
                "}";
        return movieStr;
    }
}
