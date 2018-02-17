package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class Movie: contains informations about a movie
 * <p>
 * - original title
 * - movie poster image thumbnail
 * - A plot synopsis (called overview in the api)
 * - user rating (called vote_average in the api)
 * - release date
 * - reviews
 * - traillers
 * <p>
 * Created by lsitec335.takayama on 15/08/17.
 */

public class Movie implements Parcelable {

    private long mId;
    private String mTitle;
    private String mPosterUrl;
    private String mSynopsis;
    private double mRating;
    private String mReleaseDate;
    private Review[] mReviews;
    private Trailler[] mTraillers;
//    private Integer isFavorite;

    public static final int FAVORITE_ON = 1;
    public static final int FAVORITE_OFF = 0;

    public Movie(long id, String title, String posterUrl, String sysnopsis,
                 double rating, String releaseDate) {
        setId(id);
        setTitle(title);
        setPosterUrl(posterUrl);
        setSynopsis(sysnopsis);
        setRating(rating);
        setReleaseDate(releaseDate);
//        setIsFavorite(FAVORITE_OFF);
    }

//    public Movie(long id, String title, String posterUrl, String sysnopsis,
//                 double rating, String releaseDate, Integer isFavorite) {
//        setId(id);
//        setTitle(title);
//        setPosterUrl(posterUrl);
//        setSynopsis(sysnopsis);
//        setRating(rating);
//        setReleaseDate(releaseDate);
//        setIsFavorite(isFavorite);
//    }

    public long getId() {
        return mId;
    }

    public void setId(long id) { this.mId = id; }

    public String getTitle() {
        return mTitle;
    }

    private void setTitle(String title) {
        this.mTitle = title;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    private void setPosterUrl(String posterUrl) {
        this.mPosterUrl = posterUrl;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    private void setSynopsis(String synopsis) {
        this.mSynopsis = synopsis;
    }

    public double getRating() {
        return mRating;
    }

    private void setRating(double rating) {
        this.mRating = rating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    private void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public Review[] getReviews(){ return mReviews; }

    public void setReviews(Review[] reviews){ this.mReviews = reviews; }

    public Trailler[] getTraillers(){ return mTraillers; }

    public void setTraillers(Trailler[] traillers){ this.mTraillers = traillers; }

//    public Integer getIsFavorite(){ return isFavorite; }

//    public void setIsFavorite(Integer isFavorite){ this.isFavorite = isFavorite; }

    @Override
    public String toString() {
        return "Movie:{id:" + getId() +
                ",title:" + getTitle() +
                ",posterUrl:" + getPosterUrl() +
                ",synopsis:" + getSynopsis() +
                ",rating:" + getRating() +
                ",releaseDate:" + getReleaseDate() +
                ",traillers:" + getTraillers() +
                ",reviews:" + getReviews() +
//                ",isFavorite:" + getIsFavorite() +
                "}";
    }

    //methods below exists to implement Parcelable interface
//    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mPosterUrl);
        parcel.writeString(mSynopsis);
        parcel.writeDouble(mRating);
        parcel.writeString(mReleaseDate);
        parcel.writeTypedArray(mReviews, 0);
        parcel.writeTypedArray(mTraillers, 0);
//        parcel.writeInt(isFavorite);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    //constructor for Parcelable
    private Movie(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mPosterUrl = in.readString();
        mSynopsis = in.readString();
        mRating = in.readDouble();
        mReleaseDate = in.readString();
        mReviews = in.createTypedArray(Review.CREATOR);
        mTraillers = in.createTypedArray(Trailler.CREATOR);
//        isFavorite = in.readInt();
    }
}
