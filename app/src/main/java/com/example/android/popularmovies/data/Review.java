package com.example.android.popularmovies.data;

import java.io.Serializable;

/**
 * Created by lsitec335.takayama on 24/01/18.
 */

public class Review implements Serializable {

    private String mAuthor;
    private String mContent;
    private String mUrlString;

    public Review(String author, String content, String urlString){

    }

    public String getAuthor(){
        return mAuthor;
    }

    public void setAuthor(String author){
        this.mAuthor = author;
    }

    public String getContent(){
        return mContent;
    }

    public void setContent(String content){
        this.mContent = content;
    }

    public String getUrlString(){
        return mUrlString;
    }

    public void setUrlString(String urlString){
        this.mUrlString = urlString;
    }

    @Override
    public String toString() {
        return "Movie:{author:" + getAuthor() +
                ",content:" + getContent() +
                ",url:" + getUrlString() +
                "}";
    }
}
